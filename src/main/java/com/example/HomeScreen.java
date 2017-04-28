package com.example;


import com.example.CustomerClient.Customer;
import com.example.CustomerClient.CustomerModalWindow;
import com.example.CustomerClient.CustomerSQL;
import com.example.CustomerOrder.CustomerOrder;
import com.example.CustomerOrder.CustomerOrderSQL;
import com.example.CustomerOrder.ModalWindowOrder;
import com.vaadin.data.Binder;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringUI
public class HomeScreen extends UI {

    @Autowired
    protected CustomerSQL service;
    @Autowired
    protected CustomerOrderSQL customerOrderSQL;

    protected Customer customer;
    protected CustomerOrder customerOrder;

    protected Binder<Customer> binder = new Binder<>(Customer.class);
    protected Binder<CustomerOrder> binderOrder = new Binder<>(CustomerOrder.class);

    protected Grid<Customer> grid = new Grid<>(Customer.class);
    protected Grid<CustomerOrder> gridOrder = new Grid<>(CustomerOrder.class);

    protected TextField filterText = new TextField();


    protected TextField firstName = new TextField("Имя");
    protected TextField lastName = new TextField("Фамилия");
    protected TextField patronymic = new TextField("Отчество");
    protected TextField telephoneNumber = new TextField("Телефон");


    protected TextField description = new TextField("Описание");
    protected TextField client_order = new TextField("Клиент");
    protected TextField date_creation = new TextField("Начало работ");
    protected TextField date_complete = new TextField("Окончание работ");
    protected TextField price = new TextField("Цена");
    protected NativeSelect<String> status = new NativeSelect<>("Статус");



    private Button edit = new Button("Изменить");
    private Button upload = new Button("Добавить",e -> uploadCustomer());
    private Button delete = new Button("Удалить",e -> deleteCustomer());

    private Button editOrder = new Button("Изменить");
    private Button uploadOrder = new Button("Добавить",e -> uploadCustomerOrder());
    private Button deleteOrder = new Button("Удалить",e -> deleteCustomerOrder());


    protected void init(VaadinRequest request) {

        uploadMessager();
        TabSheet tabSheet = new TabSheet();
        tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
        tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

        for (int i = 0; i < 1; i++) {

            final Label label = new Label();
            final VerticalLayout client = new VerticalLayout(label);

            updateCustomer();
            tableCreateMessage();
            grid.setColumns("lastName","firstName","patronymic","telephoneNumber");
            grid.setSizeFull();
            grid.addSelectionListener(e -> updateForm());

            VerticalLayout databaseGrid = new VerticalLayout(grid);
            HorizontalLayout buttonEditor = new HorizontalLayout(upload,edit,delete);

            setContent(client);

            client.addComponent(databaseGrid);
            client.addComponent(buttonEditor);

            tabSheet.addTab(client, "Клиент");
            {
                final Label label2 = new Label();
                final VerticalLayout order = new VerticalLayout(label2);

                updateCustomerOrder();

                gridOrder.setColumns("description","client_order","date_creation","date_complete","price","status");
                gridOrder.setSizeFull();
                gridOrder.addSelectionListener(e -> updateFormOrder());


                filterText.setPlaceholder("Фильтр клиента...");
                filterText.addValueChangeListener(e -> textFilter());
                filterText.setValueChangeMode(ValueChangeMode.LAZY);


                Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
                clearFilterTextBtn.setDescription("Фильр очищен");
                clearFilterTextBtn.addClickListener(e -> filterText.clear());

                CssLayout filtering = new CssLayout();
                filtering.addComponents(filterText, clearFilterTextBtn);
                filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);


                VerticalLayout databaseGridOrder = new VerticalLayout(filtering,gridOrder);
                HorizontalLayout buttonEditorOrder = new HorizontalLayout(uploadOrder, editOrder, deleteOrder);

                setContent(order);

                order.addComponent(databaseGridOrder);
                order.addComponent(buttonEditorOrder);

                tabSheet.addTab(order, "Заказ");
            }

        }

        edit.addClickListener(e -> {
            CustomerModalWindow customerModalWindow = new CustomerModalWindow();
            tableCreateMessage();
            customerModalWindow.grid.setItems(customer);
            customerModalWindow.ok.addClickListener(clickEvent -> saveCustomer());
        });

        editOrder.addClickListener(e -> {
            ModalWindowOrder modalWindowOrder = new ModalWindowOrder();
            tableCreateMessage();
            modalWindowOrder.gridOrder.setItems(customerOrder);
            modalWindowOrder.okOrder.addClickListener(clickEvent -> saveCustomerOrder());
        });
        VerticalLayout layout = new VerticalLayout(tabSheet);

        setContent(layout);

    }
    private void tableCreateMessage(){
        Notification notifOrder = new Notification(
                "Для редактирования",
                "Выделите строку мышкой",
                Notification.TYPE_WARNING_MESSAGE);
        notifOrder.setDelayMsec(5000);
        notifOrder. setPosition(Position.TOP_CENTER);
        notifOrder.setStyleName("mystyle");
        notifOrder.show(Page.getCurrent());
    }
    private void uploadMessager(){
        Notification notifOrder = new Notification(
                "Для добавления строки",
                "Выделите пустую строку мышкой",
                Notification.TYPE_WARNING_MESSAGE);
        notifOrder.setDelayMsec(6000);
        notifOrder. setPosition(Position.BOTTOM_LEFT);
        notifOrder.setStyleName("mystyle");
        notifOrder.show(Page.getCurrent());
    }


    protected void  updateCustomer()  {
        List<Customer> customers = service.findAll();
        grid.setItems(customers);
        setFormVisible(false);
    }
    protected void  updateCustomerOrder()  {
        List<CustomerOrder> customerOrders = customerOrderSQL.findAllOrders();
        gridOrder.setItems(customerOrders);
        setFormVisibleOrder(true);


    }
   protected void setFormVisibleOrder(boolean visible) {
        description.setVisible(visible);
        client_order.setVisible(visible);
        date_creation.setVisible(visible);
        date_complete.setVisible(visible);
        price.setVisible(visible);
        status.setVisible(visible);

    }
    protected void updateFormOrder() {
        if (gridOrder.asSingleSelect().isEmpty()) {
            setFormVisibleOrder(false);
        } else {
            customerOrder = gridOrder.asSingleSelect().getValue();
            binderOrder.setBean(customerOrder);
            setFormVisibleOrder(true);
        }

    }


    protected void updateForm() {
        if (grid.asSingleSelect().isEmpty()){
            setFormVisible(false);
        } else {
            customer = grid.asSingleSelect().getValue();
            binder.setBean(customer);
            setFormVisible(true);
        }

    }



    protected void setFormVisible(boolean visible) {
        firstName.setVisible(visible);
        lastName.setVisible(visible);
        patronymic.setVisible(visible);
        telephoneNumber.setVisible(visible);
       // save.setVisible(visible);
    }


    protected void saveCustomer() {
        service.updateLine(customer);
        updateCustomer();
    }
    protected void saveCustomerOrder() {
        customerOrderSQL.updateLineOrder(customerOrder);
        updateCustomerOrder();

    }

    private void uploadCustomer(){
        service.uploadLine(customer);
        updateCustomer();
        uploadMessage();
    }

    private void uploadCustomerOrder(){
        customerOrderSQL.uploadLineOrder(customerOrder);
        updateCustomerOrder();
        uploadMessage();
    }
    private void uploadMessage(){
        Notification notif = new Notification(
                "Строка",
                "добавлена",
                Notification.TYPE_WARNING_MESSAGE);
        notif.setDelayMsec(700);
        notif.setPosition(Position.TOP_RIGHT);
        notif.setStyleName("mystyle");
        notif.show(Page.getCurrent());
    }

    private void deleteCustomer(){
        service.deleteLine(customer);
        updateCustomer();
        deleteMessage();

    }
    private void deleteCustomerOrder(){
        customerOrderSQL.deleteLineOrder(customerOrder);
        updateCustomerOrder();
        deleteMessage();

    }

    private void deleteMessage(){
        Notification notif = new Notification(
                "Строка",
                "удалена",
                Notification.TYPE_WARNING_MESSAGE);
        notif.setDelayMsec(700);
        notif.setPosition(Position.TOP_RIGHT);
        notif.setStyleName("mystyle");
        notif.show(Page.getCurrent());
    }
       protected void textFilter(){
            List<CustomerOrder> customerses = customerOrderSQL.filterText(filterText.getValue());
            gridOrder.setItems(customerses);
        }


}