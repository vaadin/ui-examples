package com.vaadin.invoice.editor;

import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.board.Row;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.vaadin.invoice.editor.Description.getRandomDescription;
import static com.vaadin.invoice.editor.Currency.getRandomCurrency;
import static com.vaadin.invoice.editor.Category.getRandomCategory;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Invoice Editor", shortName = "Invoice Editor")
@HtmlImport("frontend://styles/shared-styles.html")
@HtmlImport("frontend://src/link-banner.html")
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
        detailsWrapper.setClassName("invoice-details");

        Span invoiceNameHeader = new Span("Invoice #3225");
        Span draftedSpan = new Span("Draft saved 5 minutes ago");
        draftedSpan.setClassName("small");

        detailsWrapper.add(invoiceNameHeader, draftedSpan);

        // Buttons
        HorizontalLayout buttonsWrapper = new HorizontalLayout();
        buttonsWrapper.addClassName("controls-line-buttons");
        
        Button discardBtn = new Button("Discard changes", e -> Notification.show("Changes were discarded!"));
        discardBtn.setThemeName("error tertiary");

        Button saveDraftBtn = new Button("Save draft", e -> Notification.show("Changes were saved!"));
        saveDraftBtn.setThemeName("tertiary");

        Button sendBtn = new Button("Send", e -> Notification.show("Invoice was sent!"));
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
        employee.setLabel("Employee");

        DatePicker date = new DatePicker();
        date.setValue(LocalDate.of(2018, 12, 12));
        date.setLabel("Date");

        inputsFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("30em", 2));
        inputsFormLayout.setId("inputs");
        inputsFormLayout.add(invoiceName, employee, date);

        RichTextEditor rte = new RichTextEditor();
        rte.setThemeName("compact");
        rte.setValue("[{\"attributes\":{\"bold\":true},\"insert\":\"Team lunch participants:\"},{\"insert\":\" Manolo, Joonas, and Matti\\nTraveling in Antwerp:\\nMetro from the hotel to the venue\"},{\"attributes\":{\"list\":\"bullet\"},\"insert\":\"\\n\"},{\"insert\":\"Taxi from the airport to the hotel\"},{\"attributes\":{\"list\":\"bullet\"},\"insert\":\"\\n\"}]");
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
        grid.setItems(createItems());
        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_COMPACT);

        grid.addEditColumn(Invoice::getProduct).text((item, newValue) -> displayNotification("Product", item, newValue)).setHeader("Product");
        grid.addEditColumn(Invoice::getDescription).text((item, newValue) -> displayNotification("Description", item, newValue)).setHeader("Description").setWidth("250px");
        grid.addEditColumn(Invoice::getPrice).text((item, newValue) -> displayNotification("Price", item, newValue)).setHeader("Price").setTextAlign(ColumnTextAlign.END);
        grid.addEditColumn(Invoice::getCurrency).select((item, newValue) -> displayNotification("Currency", item, newValue.getStringRepresentation()), Currency.class).setHeader("Currency").setWidth("150px");
        grid.addEditColumn(Invoice::getVat).text((item, newValue) -> displayNotification("VAT", item, newValue)).setHeader("VAT").setTextAlign(ColumnTextAlign.END);
        grid.addEditColumn(Invoice::getAmount).text((item, newValue) -> displayNotification("Amount", item, newValue)).setHeader("Amount").setTextAlign(ColumnTextAlign.END);
        grid.addEditColumn(Invoice::getCategory).select((item, newValue) -> displayNotification("Category", item, newValue.getStringRepresentation()), Category.class).setHeader("Category").setWidth("200px");
        grid.addEditColumn(Invoice::getOrderCompleted).checkbox((item, newValue) -> displayNotification("Order completed ", item, newValue.toString())).setHeader("Order completed");
        grid.addEditColumn(Invoice::getTotal).text((item, newValue) -> displayNotification("Total", item, newValue)).setHeader("Total").setTextAlign(ColumnTextAlign.END);
        grid.addComponentColumn(item -> createRemoveButton(grid, item))
                .setWidth("40px").setWidth("50px").setFlexGrow(0).setTextAlign(ColumnTextAlign.CENTER);

        // Details line
        Div flexBlock = new Div();
        flexBlock.setClassName("flex-1");

        Div detailsLine = new Div();

        Div total = new Div();

        Select<String> totalSelect = new Select<>("USD", "EUR", "GBP");
        totalSelect.getElement().setAttribute("theme", "custom");
        totalSelect.setClassName("currency-selector");

        Span totalText = new Span();
        totalText.setText("Total in ");

        Span priceText = new Span();
        priceText.setText(" 812");

        total.add(totalText, totalSelect, priceText);

        detailsLine.add(flexBlock, total);
        detailsLine.setClassName("controls-line");

        add(controlsLine, board, addsLine, grid, detailsLine);
    }

    private Button createRemoveButton(GridPro<Invoice> grid, Invoice item) {
        Button button = new Button(new Icon(VaadinIcon.CLOSE), clickEvent -> {
            ListDataProvider<Invoice> dataProvider = (ListDataProvider<Invoice>) grid.getDataProvider();
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        });
        button.setClassName("delete-button");
        return button;
    }

    private static void displayNotification(String propertyName, Invoice item, String newValue) {
        Notification.show(
                propertyName + " was changed to " + newValue + " for item: " + item.toString());
    }

    private static List<Invoice> createItems() {
        Random random = new Random(0);
        return IntStream.range(1, 500)
                .mapToObj(index -> createInvoice(index, random))
                .collect(Collectors.toList());
    }

    private static Invoice createInvoice(int index, Random random) {
        Invoice invoice = new Invoice();
        invoice.setId(index);
        invoice.setProduct("PVR2019");
        invoice.setDescription(getRandomDescription());
        invoice.setPrice(random.nextInt(100000) / 100f);
        invoice.setCurrency(getRandomCurrency());
        invoice.setVat(1 + random.nextInt((24 - 1) + 1));
        invoice.setAmount(1 + random.nextInt((10 - 1) + 1));
        invoice.setCategory(getRandomCategory());
        invoice.setOrderCompleted(Boolean.TRUE);
        invoice.setTotal(1 + random.nextInt((1000 - 1) + 1));

        return invoice;
    }
}
