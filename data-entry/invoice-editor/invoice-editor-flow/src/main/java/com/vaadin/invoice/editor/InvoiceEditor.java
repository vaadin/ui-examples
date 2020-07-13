package com.vaadin.invoice.editor;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import static com.vaadin.invoice.editor.Category.getRandomCategory;
import static com.vaadin.invoice.editor.Currency.getRandomCurrency;
import static com.vaadin.invoice.editor.Description.getRandomDescription;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@JsModule("./styles/shared-styles.js")
@JsModule("./src/link-banner.js")
public class InvoiceEditor extends Div {

    public InvoiceEditor() {
        setId("container");

        this.getElement().appendChild(new Element("link-banner"));

        // Controls part
        Div controlsLine = new Div();
        controlsLine.addClassName("controls-line");

        // Content
        HorizontalLayout detailsWrapper = new HorizontalLayout();
        detailsWrapper.getThemeList().add("padding");
        detailsWrapper.getThemeList().remove("spacing");
        detailsWrapper.setClassName("invoice-details");

        Span invoiceNameHeader = new Span("Invoice #3225");
        Span draftedSpan = new Span("Draft saved 5 minutes ago");
        draftedSpan.setClassName("small");

        detailsWrapper.add(invoiceNameHeader, draftedSpan);

        // Buttons
        HorizontalLayout buttonsWrapper = new HorizontalLayout();
        buttonsWrapper.getThemeList().remove("spacing");
        buttonsWrapper.addClassName("controls-line-buttons");

        Button discardBtn = new Button("Discard changes",
                e -> Notification.show("Changes were discarded!"));
        discardBtn.setThemeName("error tertiary");

        Button saveDraftBtn = new Button("Save draft",
                e -> Notification.show("Changes were saved!"));
        saveDraftBtn.setThemeName("tertiary");

        Button sendBtn = new Button("Send",
                e -> Notification.show("Invoice was sent!"));
        sendBtn.setThemeName("primary");

        buttonsWrapper.add(discardBtn, saveDraftBtn, sendBtn);

        controlsLine.add(detailsWrapper, buttonsWrapper);

        // Input parts layout
        Board board = new Board();

        FormLayout inputsFormLayout = new FormLayout();
        Div inputsFormWrapper = new Div();
        inputsFormWrapper.add(inputsFormLayout);

        // Inputs
        TextField invoiceName = new TextField();
        invoiceName.getElement().setAttribute("colspan", "2");
        invoiceName.setLabel("Invoice Name");
        invoiceName.setClassName("large");
        invoiceName.setValue("Trip to Italy");

        Select<String> employee = new Select<>("Manolo", "Joonas", "Matti");
        employee.setValue("Manolo");
        employee.setLabel("Employee");

        DatePicker date = new DatePicker();
        date.setValue(LocalDate.of(2018, 12, 12));
        date.setLabel("Date");

        inputsFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1,
                        FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("30em", 2));
        inputsFormLayout.setId("inputs");
        inputsFormLayout.add(invoiceName, employee, date);

        RichTextEditor rte = new RichTextEditor();
        rte.setThemeName("compact");
        rte.setValue(
                "[{\"attributes\":{\"bold\":true},\"insert\":\"Team lunch participants:\"},{\"insert\":\" Manolo, Joonas, and Matti\\nTraveling in Antwerp:\\nMetro from the hotel to the venue\"},{\"attributes\":{\"list\":\"bullet\"},\"insert\":\"\\n\"},{\"insert\":\"Taxi from the airport to the hotel\"},{\"attributes\":{\"list\":\"bullet\"},\"insert\":\"\\n\"}]");
        Div rteWrapper = new Div();
        rteWrapper.add(rte);
        Row dataRow = board.addRow(inputsFormWrapper, rteWrapper);
        dataRow.setComponentSpan(rteWrapper, 2);

        // Adds line
        Div addsLine = new Div();
        addsLine.setClassName("controls-line");

        // Buttons
        Div btnWrapper = new Div();
        btnWrapper.setClassName("flex-1");

        Span cardTransactionText = new Span("Add credit card transaction");
        Button addCardTransactionBtn = new Button(cardTransactionText);
        addCardTransactionBtn.setThemeName("tertiary");
        addCardTransactionBtn.setId("add-transaction");
        btnWrapper.add(addCardTransactionBtn);

        addsLine.add(btnWrapper);

        // Grid Pro
        GridPro<Invoice> grid = new GridPro<>();
        List<Invoice> invoiceList = createItems();
        grid.setItems(invoiceList);

        addCardTransactionBtn.addClickListener(e -> {
            invoiceList.add(0, new Invoice("", "", 0, Currency.EUR, 0, 0,
                    Category.PERSONAL, false, 0));
            grid.getDataProvider().refreshAll();
        });

        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS,
                GridVariant.LUMO_COMPACT);

        grid.addEditColumn(Invoice::getProduct, "product")
                .text((item, newValue) -> {
                    item.setProduct(newValue);
                    displayNotification("Product", item, newValue);
                }).setHeader("Product");
        grid.addEditColumn(Invoice::getDescription, "description")
                .text((item, newValue) -> {
                    item.setDescription(newValue);
                    displayNotification("Description", item, newValue);
                }).setHeader("Description").setWidth("250px");
        grid.addEditColumn(Invoice::getPrice, "price")
                .text((item, newValue) -> {
                    try {
                        item.setPrice(Float.valueOf(newValue));
                        displayNotification("Price", item, newValue);
                    } catch (Exception e) {
                        displayNotification("Price", item);
                    }
                }).setHeader("Price").setTextAlign(ColumnTextAlign.END);

        ComponentRenderer<Div, Invoice> currencyRenderer = new ComponentRenderer<>(
                invoice -> {
                    Div icon = new Div();
                    icon.setText(invoice.getCurrency().name());
                    icon.setClassName("icon-"
                            + invoice.getCurrency().name().toLowerCase());
                    return icon;
                });

        grid.addEditColumn(Invoice::getCurrency, currencyRenderer)
                .select((item, newValue) -> {
                    item.setCurrency(newValue);
                    displayNotification("Currency", item,
                            newValue.getStringRepresentation());
                }, Currency.class)
                .setComparator(Comparator.comparing(
                        inv -> inv.getCurrency().getStringRepresentation()))
                .setHeader("Currency").setWidth("150px");

        grid.addEditColumn(Invoice::getVat,
                TemplateRenderer.<Invoice> of("[[item.vat]]%")
                        .withProperty("vat", Invoice::getVat))
                .text((item, newValue) -> {
                    try {
                        item.setVat(Integer.valueOf(newValue));
                        displayNotification("VAT", item, newValue);
                    } catch (Exception e) {
                        displayNotification("VAT", item);
                    }
                }).setComparator(Comparator.comparing(inv -> inv.getVat()))
                .setHeader("VAT").setTextAlign(ColumnTextAlign.END);
        grid.addEditColumn(Invoice::getAmount, "amount")
                .text((item, newValue) -> {
                    try {
                        item.setAmount(Integer.valueOf(newValue));
                        displayNotification("Amount", item, newValue);
                    } catch (Exception e) {
                        displayNotification("Amount", item);
                    }
                }).setHeader("Amount").setTextAlign(ColumnTextAlign.END);
        grid.addEditColumn(Invoice::getCategory).select((item, newValue) -> {
            item.setCategory(newValue);
            displayNotification("Category", item,
                    newValue.getStringRepresentation());
        }, Category.class)
                .setComparator(Comparator.comparing(
                        inv -> inv.getCategory().getStringRepresentation()))
                .setHeader("Category").setWidth("200px");

        ComponentRenderer<Span, Invoice> statusRenderer = new ComponentRenderer<>(
                invoice -> {
                    Span badge = new Span();
                    badge.setText(
                            invoice.getOrderCompleted() ? "Completed" : "Open");
                    badge.getElement().setAttribute("theme",
                            invoice.getOrderCompleted() ? "badge success"
                                    : "badge");
                    return badge;
                });
        grid.addEditColumn(Invoice::getOrderCompleted, statusRenderer)
                .checkbox((item, newValue) -> {
                    item.setOrderCompleted(newValue);
                    displayNotification("Order completed ", item,
                            newValue.toString());
                })
                .setComparator(
                        Comparator.comparing(inv -> inv.getOrderCompleted()))
                .setHeader("Status");
        grid.addEditColumn(Invoice::getTotal,
                TemplateRenderer.<Invoice> of("[[item.symbol]][[item.total]]")
                        .withProperty("symbol",
                                invoice -> invoice.getCurrency().getSymbol())
                        .withProperty("total", Invoice::getTotal))
                .text((item, newValue) -> {
                    try {
                        item.setTotal(Integer.valueOf(newValue));
                        displayNotification("Total", item, newValue);
                    } catch (Exception e) {
                        displayNotification("Total", item);
                    }
                }).setComparator(Comparator.comparing(inv -> inv.getTotal()))
                .setHeader("Total").setTextAlign(ColumnTextAlign.END);
        grid.addComponentColumn(item -> createRemoveButton(grid, item))
                .setWidth("70px").setFlexGrow(0)
                .setTextAlign(ColumnTextAlign.CENTER);

        grid.setMultiSort(true);

        grid.getColumns().forEach(column -> column.setResizable(true));

        // Details line
        Div detailsLine = new Div();

        Select<String> totalSelect = new Select<>("USD", "EUR", "GBP");
        totalSelect.setValue("EUR");
        totalSelect.getElement().setAttribute("theme", "custom");
        totalSelect.setClassName("currency-selector");

        Span totalText = new Span();
        totalText.setText("Total in ");

        Span priceText = new Span();
        priceText.setClassName("total");
        priceText.setText("812");

        detailsLine.add(totalText, totalSelect, priceText);
        detailsLine.setClassName("controls-line footer");

        add(controlsLine, board, addsLine, grid, detailsLine);
    }

    private Button createRemoveButton(GridPro<Invoice> grid, Invoice item) {
        Button button = new Button(new Icon(VaadinIcon.CLOSE), clickEvent -> {
            ListDataProvider<Invoice> dataProvider = (ListDataProvider<Invoice>) grid
                    .getDataProvider();
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        });
        button.setClassName("delete-button");
        button.addThemeName("small");
        return button;
    }

    private static void displayNotification(String propertyName, Invoice item,
            String newValue) {
        Notification.show(propertyName + " was updated to be: " + newValue
                + " for product " + item.toString());
    }

    private static void displayNotification(String propertyName, Invoice item) {
        Notification.show(
                propertyName + " cannot be set for product " + item.toString());
    }

    private static List<Invoice> createItems() {
        Random random = new Random(0);
        return IntStream.range(1, 14)
                .mapToObj(index -> createInvoice(index, random))
                .collect(Collectors.toList());
    }

    private static Invoice createInvoice(int index, Random random) {
        Invoice invoice = new Invoice();
        invoice.setProduct("PVR2019");
        invoice.setDescription(getRandomDescription());
        invoice.setPrice(random.nextInt(100000) / 100f);
        invoice.setCurrency(getRandomCurrency());
        invoice.setVat(1 + random.nextInt((24 - 1) + 1));
        invoice.setAmount(1 + random.nextInt((10 - 1) + 1));
        invoice.setCategory(getRandomCategory());
        invoice.setOrderCompleted(random.nextBoolean());
        invoice.setTotal(1 + random.nextInt((1000 - 1) + 1));

        return invoice;
    }
}
