package com.example.CustomerOrder;

import com.example.HomeScreen;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Created by Павел on 22.04.2017.
 */


public class ModalWindowOrder extends HomeScreen {

    public Button okOrder = new Button("ОК");
    private Button cancelOrder = new Button("Отменить");



    public ModalWindowOrder(){

        Window modalWindowOrder = new Window();
        modalWindowOrder.setModal(true);
        modalWindowOrder.setClosable(false);
        modalWindowOrder.setResizable(false);



        gridOrder.setColumns("description","client_order","date_creation","date_complete","price","status");
        gridOrder.setSizeFull();
        setFormVisibleOrder(true);
        gridOrder.addSelectionListener(event -> updateFormOrder());


        status.setItems("Запланирован","Выполнен","Принят клиентом");
        status.setEmptySelectionAllowed(false);
        status.addValueChangeListener(event -> Notification.show("Статус обновлен:",
                String.valueOf(event.getValue()),
                Notification.Type.TRAY_NOTIFICATION));

        try {
                binderOrder.bindInstanceFields(this);
            }catch (IllegalStateException ex){
                ex.printStackTrace();
            }


        VerticalLayout formLayout = new VerticalLayout(gridOrder);
        HorizontalLayout horizontalLayout = new HorizontalLayout(description,client_order, date_creation, date_complete,price,status);
        HorizontalLayout layout = new HorizontalLayout(okOrder,cancelOrder);

        okOrder.addClickListener(clickEvent -> tablerUpdateMessage());
        okOrder.addClickListener(clickEvent -> modalWindowOrder.close());
        cancelOrder.addClickListener(clickEvent -> modalWindowOrder.close());


        modalWindowOrder.setContent(formLayout);
        formLayout.addComponent(horizontalLayout);
        formLayout.addComponent(layout);
        UI.getCurrent().addWindow(modalWindowOrder);

    }

    private void tablerUpdateMessage(){
        Notification notifOrder = new Notification(
                "Таблица",
                "обновленна",
                Notification.TYPE_WARNING_MESSAGE);
        notifOrder.setDelayMsec(699);
        notifOrder. setPosition(Position.TOP_RIGHT);
        notifOrder.setStyleName("mystyle");
        notifOrder.show(Page.getCurrent());
    }




    @Override
    public void forEach(Consumer<? super Component> action) {

    }

    @Override
    public Spliterator<Component> spliterator() {
        return null;
    }
}
