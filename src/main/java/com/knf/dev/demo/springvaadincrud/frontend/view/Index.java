package com.knf.dev.demo.springvaadincrud.frontend.view;

import org.springframework.util.StringUtils;
import com.knf.dev.demo.springvaadincrud.backend.model.Search;
import com.knf.dev.demo.springvaadincrud.backend.model.Gitrepository;
import com.knf.dev.demo.springvaadincrud.backend.
                              repository.GitrepositoryRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value="/")
public class Index extends VerticalLayout {

    final Grid<Gitrepository> grid;
    final TextField filter;
    private final GitrepositoryRepository repo;
    private final Button addNewBtn;
    private final SearchEditor editor;

    public Index(GitrepositoryRepository repo, SearchEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Gitrepository.class);
        this.filter = new TextField();
        this.addNewBtn = new Button
            ("Add Search",VaadinIcon.PLUS.create());
        addNewBtn.addThemeVariants
            (ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_CONTRAST);

        // build layout
        HorizontalLayout actions = new
                HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);
        grid.setHeight("300px");
        grid.setColumns("id", "organizationName", "topRepositories", "oldestForks");
        grid.getColumnByKey("id").setWidth("60px").
                setFlexGrow(0);
        filter.setPlaceholder("Filter by email");

        // Hook logic to components
        /* Replace listing with filtered content when user
          changes filter*/
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener
                 (e -> listSearches(e.getValue()));

        /* Connect selected User to editor or hide if none
            is selected */
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editSearch(e.getValue());
        });

        /* Instantiate and edit new
        User the new button is clicked
         */
        addNewBtn.addClickListener(e -> editor.editSearch
                (new Gitrepository("", "")));

        // Listen changes made by the editor,
        // refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listSearches(filter.getValue());
        });

        // Initialize listing
        listSearches(null);
    }

    void listSearches(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        }
        // } else {
        //     grid.setItems(repo.
        //           findByEmailStartsWithIgnoreCase(filterText));
        // }
    }
}
