package com.vaadin.customer.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.crud.CrudI18n;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route("")
@PWA(name = "Customer CRUD", shortName = "Customer CRUD")
public class CustomerCRUD extends VerticalLayout {

    public CustomerCRUD() {
        setSizeFull();
        ListDataProvider<Company> dataProvider = createDataProvider();
        Crud<Company> crud = new Crud<>(Company.class, createGrid(), createCompanyEditor());
        crud.setMaxWidth("800px");
        crud.setWidth("100%");
        crud.setDataProvider(dataProvider);
        setHorizontalComponentAlignment(Alignment.CENTER, crud);

        CrudI18n customI18n = CrudI18n.createDefault();
        customI18n.setEditItem("Update Customer");
        customI18n.setNewItem("New Customer");
        crud.setI18n(customI18n);

        crud.addSaveListener(saveEvent -> {
            Company toSave = saveEvent.getItem();
            // Save the item in the database
            if (!dataProvider.getItems().contains(toSave)) {
                dataProvider.getItems().add(toSave);
            }
        });

        crud.addDeleteListener(deleteEvent -> {
            // Delete the item in the database
            dataProvider.getItems().remove(deleteEvent.getItem());
        });

        add(crud);
    }

    private ListDataProvider<Company> createDataProvider() {
        List<Company> data = new ArrayList<>();
        data.add(new Company("ACME Corporation", "Rolf Hegbl",
                "rolf.hegbl@acme.com", "Ruukinkatu 346", "Turku", "20540",
                "Southern Finland", "Finland", "123-234-345", "987-876-756"));
        data.add(new Company("Google", "Sergey Brin", "sergey@google.com",
                "Spear str 345", "San Francisco", "94105", "California",
                "United States", "123-456-789", "987-654-321"));
        return new ListDataProvider<>(data);
    }

    private Grid<Company> createGrid() {
        Grid<Company> grid = new Grid<>();
        grid.addColumn(c -> c.getCompanyName()).setHeader("Company Name")
                .setWidth("160px");
        grid.addColumn(c -> c.getContactName()).setHeader("Contact Person");
        grid.addColumn(c -> c.getPhone()).setHeader("Phone");
        Crud.addEditColumn(grid);
        return grid;
    }

    private CrudEditor<Company> createCompanyEditor() {
        TextField companyName = new TextField("Company Name");
        setColspan(companyName, 4);
        TextField contactName = new TextField("Contact Name");
        setColspan(contactName, 2);
        TextField contactEmail = new TextField("Contact Email");
        setColspan(contactEmail, 2);

        TextField address = new TextField("Address");
        setColspan(address, 2);

        TextField city = new TextField("City");
        TextField zip = new TextField("Postal/Zip Code");

        TextField region = new TextField("Region");
        setColspan(region, 2);

        ComboBox<String> country = new ComboBox<>();
        country.setAllowCustomValue(true);
        country.setLabel("Country");
        setColspan(country, 2);

        country.setItems(getCountriesList());
        TextField phone = new TextField("Phone");
        setColspan(phone, 2);
        TextField fax = new TextField("Fax");
        setColspan(fax, 2);

        FormLayout form = new FormLayout(companyName, contactName, contactEmail,
                address, city, zip, region, country, phone, fax);
        form.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));

        Binder<Company> binder = new Binder<>(Company.class);
        binder.bind(companyName, Company::getCompanyName, Company::setCompanyName);
        binder.bind(contactName, Company::getContactName, Company::setContactName);
        binder.bind(contactEmail, Company::getContactEmail, Company::setContactEmail);
        binder.bind(address, Company::getAddress, Company::setAddress);
        binder.bind(city, Company::getCity, Company::setCity);
        binder.bind(zip, Company::getZip, Company::setZip);
        binder.bind(region, Company::getRegion, Company::setRegion);
        binder.bind(country, Company::getCountry, Company::setCountry);
        binder.bind(phone, Company::getPhone, Company::setPhone);
        binder.bind(fax, Company::getFax, Company::setFax);

        return new BinderCrudEditor<>(binder, form);
    }

    private void setColspan(Component component, int colspan) {
        component.getElement().setAttribute("colspan", Integer.toString(colspan));
    }

    private List<String> getCountriesList() {
        return Stream.of(Locale.getISOCountries())
                .map(iso -> new Locale("", iso).getDisplayCountry()).sorted()
                .collect(Collectors.toList());
    }

}
