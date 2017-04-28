package com.example.CustomerClient;

import com.example.HomeScreen;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;

import java.util.Spliterator;
import java.util.function.Consumer;

public class CustomerModalWindow extends HomeScreen {


    public  Button ok = new Button("ОК");
    protected Button cancel = new Button("Отменить");

    public CustomerModalWindow(){

        Window modalWindow = new Window();
        modalWindow.setModal(true);
        modalWindow.setClosable(false);
        modalWindow.setResizable(false);

        grid.setColumns("lastName","firstName","patronymic","telephoneNumber");
        grid.setSizeFull();
        setFormVisible(true);
        grid.addSelectionListener(ee -> updateForm());


        binder.bindInstanceFields(this);

        VerticalLayout formLayout = new VerticalLayout(grid);
        HorizontalLayout horizontalLayout = new HorizontalLayout(lastName, firstName,patronymic,telephoneNumber);
        HorizontalLayout layout = new HorizontalLayout(ok,cancel);

                ok.addClickListener(clickEvent -> tablerUpdateMessage());
                ok.addClickListener(clickEvent -> modalWindow.close());
                cancel.addClickListener(clickEvent -> modalWindow.close());


            modalWindow.setContent(formLayout);
            formLayout.addComponent(horizontalLayout);
            formLayout.addComponent(layout);
            UI.getCurrent().addWindow(modalWindow);

    }

    private void tablerUpdateMessage(){
        Notification notif = new Notification(
                "Таблица",
                "обновленна",
                Notification.TYPE_WARNING_MESSAGE);
        notif.setDelayMsec(700);
        notif.setPosition(Position.TOP_RIGHT);
        notif.setStyleName("mystyle");
        notif.show(Page.getCurrent());
    }

    @Override
    public void forEach(Consumer<? super Component> action) {

    }

    @Override
    public Spliterator<Component> spliterator() {
        return null;
    }

}
