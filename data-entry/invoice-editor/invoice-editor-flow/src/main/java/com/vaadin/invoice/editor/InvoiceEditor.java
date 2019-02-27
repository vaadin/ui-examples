package com.vaadin.invoice.editor;

import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.vaadin.invoice.editor.Currency.getRandomCurrency;
import static com.vaadin.invoice.editor.Category.getRandomCategory;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
@PWA(name = "Invoice Editor", shortName = "Invoice Editor")
@HtmlImport("frontend://styles/shared-styles.html")
public class InvoiceEditor extends Board {

    public InvoiceEditor() {
        // Controls part
        Div controls = new Div();

        Div controlsLine = new Div();
        controlsLine.addClassName("controls-line");

        // Content
        Div detailsWrapper = new Div();
        detailsWrapper.setClassName("invoice-details");

        Span invoiceNameHeader = new Span("Invoice #3225");
        Span draftedSpan = new Span("Draft saved 5 minutes ago");

        detailsWrapper.add(invoiceNameHeader, draftedSpan);

        // Buttons
        Button discardBtn = new Button("Discard changes", e -> Notification.show("Changes were discard!"));
        discardBtn.setThemeName("error secondary");

        Button saveDraftBtn = new Button("Save draft", e -> Notification.show("Changes were saved!"));
        saveDraftBtn.setThemeName("secondary");

        Button sendBtn = new Button("Send", e -> Notification.show("Invoice was sent!"));
        sendBtn.setThemeName("primary");

        controlsLine.add(detailsWrapper, discardBtn, saveDraftBtn, sendBtn);
        controls.add(controlsLine);
        controls.getElement().setAttribute("board-cols", "2");

        // Input parts layout
        Board board = new Board();

        FormLayout inputsFormLayout = new FormLayout();
        Div inputsFormWrapper = new Div();
        inputsFormWrapper.setClassName("inputs-wrapper");
        inputsFormWrapper.add(inputsFormLayout);

        // Inputs
        TextField invoiceName = new TextField();
        invoiceName.getElement().setAttribute("colspan", "2");
        invoiceName.setLabel("Invoice Name");
        invoiceName.setClassName("large");

        Select<String> employee = new Select<>("Jose", "Manolo", "Pedro");
        employee.setLabel("Employee");

        DatePicker date = new DatePicker();
        date.setLabel("Date");

        inputsFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("20em", 2));
        inputsFormLayout.setId("inputs");
        inputsFormLayout.add(invoiceName, employee, date);

        RichTextEditor rte = new RichTextEditor();
        rte.setThemeName("compact");
        Div rteWrapper = new Div();
        rteWrapper.setClassName("rte-wrapper");
        rteWrapper.add(rte);
        board.addRow(inputsFormWrapper, rteWrapper);
        controls.add(board);

        // Adds line
        Div addsLine = new Div();
        addsLine.setClassName("controls-line");

        // Buttons
        Div btnWrapper = new Div();
        btnWrapper.setClassName("flex-1");

        Button addCardTransactionBtn = new Button("Add credit card transaction");
        addCardTransactionBtn.setThemeName("tertiary");
        addCardTransactionBtn.setId("add-transaction");
        btnWrapper.add(addCardTransactionBtn);

        Button addLine = new Button("Add Line");
        addCardTransactionBtn.setThemeName("tertiary");
        addCardTransactionBtn.setId("add-line");

        addsLine.add(btnWrapper, addLine);

        controls.add(addsLine);

        // Grid Pro
        GridPro<Invoice> grid = new GridPro<>();
        grid.setItems(createItems());
        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_COMPACT);

        grid.addEditColumn(Invoice::getProduct).text((item, newValue) -> displayNotification("Product", item, newValue)).setHeader("Product");
        grid.addEditColumn(Invoice::getDescription).text((item, newValue) -> displayNotification("Description", item, newValue)).setHeader("Description");
        grid.addEditColumn(Invoice::getPrice).text((item, newValue) -> displayNotification("Price", item, newValue)).setHeader("Price");
        grid.addEditColumn(Invoice::getCurrency).select((item, newValue) -> displayNotification("Currency", item, newValue.getStringRepresentation()), Currency.class).setHeader("Currency");
        grid.addEditColumn(Invoice::getVat).text((item, newValue) -> displayNotification("VAT", item, newValue)).setHeader("VAT");
        grid.addEditColumn(Invoice::getAmount).text((item, newValue) -> displayNotification("Amount", item, newValue)).setHeader("Amount");
        grid.addEditColumn(Invoice::getCategory).select((item, newValue) -> displayNotification("Category", item, newValue.getStringRepresentation()), Category.class).setHeader("Category");
        grid.addEditColumn(Invoice::getOrderCompleted).checkbox((item, newValue) -> displayNotification("Order completed ", item, newValue.toString())).setHeader("Order completed");
        grid.addEditColumn(Invoice::getTotal).text((item, newValue) -> displayNotification("Total", item, newValue)).setHeader("Total");
        grid.addColumn(new NativeButtonRenderer<>("X", item -> {
            ListDataProvider<Invoice> dataProvider = (ListDataProvider<Invoice>) grid
                    .getDataProvider();
            dataProvider.getItems().remove(item);
            dataProvider.refreshAll();
        })).setWidth("40px").setFlexGrow(0);
        controls.add(grid);

        // Details line
        Div flexBlock = new Div();
        flexBlock.setClassName("flex-1");

        Div detailsLine = new Div();

        Div total = new Div();

        Select<String> totalSelect = new Select<>("USD", "EUR", "GBP");
        totalSelect.setClassName("currency-selector");

        Span totalText = new Span();
        totalText.setText("Total in ");

        Span priceText = new Span();
        priceText.setText(" 812");

        total.add(totalText, totalSelect, priceText);

        detailsLine.add(flexBlock, total);
        detailsLine.setClassName("controls-line");

        controls.add(detailsLine);

        addRow(controls);
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
        invoice.setDescription("Description");
        invoice.setPrice(300);
        invoice.setCurrency(getRandomCurrency());
        invoice.setVat(24);
        invoice.setAmount(4);
        invoice.setCategory(getRandomCategory());
        invoice.setOrderCompleted(Boolean.TRUE);
        invoice.setTotal(1200);

        return invoice;
    }
}
