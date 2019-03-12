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
import com.vaadin.flow.component.textfield.EmailField;
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
        data.add(new Company("Alpha", "Julie Baroh", "julieb13@yahoo.com", "1600 Lind Ave SW", "Renton", "WA 98057", "Washington", "USA", "123-234-345", "987-876-756"));
        data.add(new Company("Arabian Nights", "Kaja Foglio", "kaja64@gmail.com", "Spear str 345", "Bellevue", "94105", "Washington", "USA", "123-456-789", "987-654-321"));
        data.add(new Company("Time Spiral", "Dan Frazier", "frazier51@hotmail.com", "", "", "", "", "USA", "(364) 065-3803", "(972) 924-7669"));
        data.add(new Company("Fallen Empires", "Jesper Myrfors", "jesper45@yahoo.com", "", "", "", "", "Sweden", "(199) 360-6272", "(972) 197-0177"));
        data.add(new Company("Morningtide", "Brian Snøddy", "brians1@gmail.com", "", "", "", "", "USA", "(644) 936-3193", "(469) 740-6825"));
        data.add(new Company("Tempest", "Susan Van Camp", "susan19@gmail.com", "", "", "", "", "USA", "(723) 338-4034", "(214) 365-4444"));
        data.add(new Company("Legends", "Liz Danforth", "lizd@hotmail.com", "", "", "", "", "USA", "(644) 273-0079", "(972) 874-5649"));
        data.add(new Company("Antiquities", "Nene Thomas", "nenet84@yahoo.com", "", "Oklahoma City", "", "Oklahoma", "USA", "(771) 509-1730", "(214) 430-7750"));
        data.add(new Company("Alara Reborn", "Anthony S. Waters", "watersa@yahoo.com", "", "", "", "", "", "(687) 162-0128", "(469) 567-6514"));
        data.add(new Company("Unglued", "David A. Cherry", "dcherry@gmail.com", "", "Lawton", "", "Oklahoma", "USA", "(074) 366-4423", "(469) 358-9525"));
        data.add(new Company("Mirage", "John Avon", "john44@yahoo.com", "", "Cardiff", "", "South Wales", "UK", "(714) 000-0438", "(469) 468-3131"));
        data.add(new Company("Weatherlight", "Ian Miller", "miller74@gmail.com", "", "London", "", "England", "UK", "(435) 307-5309", "(214) 950-6083"));
        data.add(new Company("Visions", "Tony DiTerlizzi", "tony75@gmail.com", "", "Los Angeles", "", "California", "USA", "(793) 088-1786", "(469) 042-7450"));
        data.add(new Company("Weatherlight", "Mark Harrison", "harrison96@hotmail.com", "", "Birmingham", "", "West Midlands, England", "UK", "(097) 788-2612", "(214) 746-6340"));
        data.add(new Company("Mercadian Masques", "Jeff Laubenstein", "jeffl53@hotmail.com", "", "", "", "", "", "(906) 886-5063", "(214) 406-8397"));
        data.add(new Company("Portal", "Colin MacNeil", "colinmac@hotmail.com", "", "", "", "", "UK", "(430) 300-6257", "(469) 816-2081"));
        data.add(new Company("Tempest", "Alan Pollack", "alanp13@hotmail.com", "", "", "", "New Jersey", "USA", "(786) 663-4823", "(972) 582-5980"));
        data.add(new Company("Ravnica", "Mark A. Nelson", "marka@gmail.com", "", "", "", "", "USA", "(567) 679-7562", "(214) 751-3014"));
        data.add(new Company("Zendikar", "Fred Fields", "ffields@yahoo.com", "", "", "", "", "", "(287) 205-1594", "(469) 203-4816"));
        data.add(new Company("Masters 25", "Xi Zhang", "xizhang78@live.com", "", "Kaifeng", "", "Henan", "China", "(561) 187-4547", "(214) 481-7271"));
        return new ListDataProvider<>(data);
    }

    private Grid<Company> createGrid() {
        Grid<Company> grid = new Grid<>();
        grid.addColumn(c -> c.getCompanyName()).setHeader("Company name")
                .setWidth("160px");
        grid.addColumn(c -> c.getContactName()).setHeader("Contact person");
        grid.addColumn(c -> c.getPhone()).setHeader("Phone");
        Crud.addEditColumn(grid);
        return grid;
    }

    private CrudEditor<Company> createCompanyEditor() {
        TextField companyName = new TextField("Company name");
        companyName.setRequiredIndicatorVisible(true);
        setColspan(companyName, 4);
        TextField contactName = new TextField("Contact name");
        contactName.setRequiredIndicatorVisible(true);
        setColspan(contactName, 2);
        EmailField contactEmail = new EmailField("Contact email");
        setColspan(contactEmail, 2);
        contactEmail.setRequiredIndicatorVisible(true);

        TextField address = new TextField("Address");
        setColspan(address, 2);

        TextField city = new TextField("City");
        TextField zip = new TextField("Postal/Zip code");

        TextField region = new TextField("Region");
        setColspan(region, 2);

        ComboBox<String> country = new ComboBox<>();
        country.setAllowCustomValue(true);
        country.setLabel("Country");
        setColspan(country, 2);

        country.setItems(getCountriesList());
        TextField phone = new TextField("Phone");
        setColspan(phone, 2);
        phone.setRequiredIndicatorVisible(true);
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
