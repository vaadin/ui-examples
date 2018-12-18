package com.vaadin.invoice.editor;

import com.vaadin.flow.component.board.Board;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

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
        Button discardBtn = new Button("Discard changes");
        discardBtn.setThemeName("error secondary");

        Button saveDraftBtn = new Button("Save draft");
        saveDraftBtn.setThemeName("secondary");

        Button sendBtn = new Button("Send");
        sendBtn.setThemeName("primary");

        controlsLine.add(detailsWrapper, discardBtn, saveDraftBtn, sendBtn);
        controls.add(controlsLine);
        controls.getElement().setAttribute("board-cols", "2");

        // Input parts layout
        FormLayout mainFormLayout = new FormLayout();
        FormLayout.FormItem formItemWrapper = new FormLayout.FormItem();

        FormLayout inputsFormLayout = new FormLayout();

        // Inputs
        TextField invoiceName = new TextField();
        invoiceName.getElement().setAttribute("colspan", "2");
        invoiceName.setLabel("Invoice Name");
        invoiceName.setClassName("large");

        TextField employee = new TextField();
        employee.setLabel("Employee");

        DatePicker date = new DatePicker();
        date.setLabel("Date");

        inputsFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("20em", 2));
        inputsFormLayout.add(invoiceName, employee, date);
        formItemWrapper.add(inputsFormLayout);

        mainFormLayout.add(formItemWrapper);
        mainFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0",1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("45em", 2));
        controls.add(mainFormLayout);

        RichTextEditor rte = new RichTextEditor();
        rte.setThemeName("compact");
        FormLayout.FormItem rteFormItem = new FormLayout.FormItem();
        rteFormItem.add(rte);
        mainFormLayout.add(rteFormItem);

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

        // Grid
        Grid grid = new Grid();

        controls.add(grid);

        // Details line
        Div flexBlock = new Div();
        flexBlock.setClassName("flex-1");

        Div detailsLine = new Div();

        Div total = new Div();
        total.setText("Total: $");

        detailsLine.add(flexBlock, total);
        detailsLine.setClassName("controls-line");

        controls.add(detailsLine);

        //receipt-viewer
        Div receiptViewer = new Div();

        // Tabs
        Tabs tabs = new Tabs();

        Tab preview = new Tab("Preview");
        Tab receipts = new Tab("Receipts");

        tabs.add(preview, receipts);
        receiptViewer.add(tabs);

        addRow(controls, receiptViewer);
    }
}
