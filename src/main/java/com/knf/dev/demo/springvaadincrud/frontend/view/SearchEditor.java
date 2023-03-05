package com.knf.dev.demo.springvaadincrud.frontend.view;

import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;
import com.knf.dev.demo.springvaadincrud.backend.model.Search;
import com.knf.dev.demo.springvaadincrud.backend.model.Gitrepository;
import com.knf.dev.demo.springvaadincrud.backend
                           .repository.GitrepositoryRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component
                          .orderedlayout.HorizontalLayout;
import com.vaadin.flow.component
                           .orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
public class SearchEditor extends VerticalLayout
                          implements KeyNotifier {

    private final GitrepositoryRepository repository;
    /* Fields to edit properties in Search entity */
    TextField organizationName = new TextField("Organization Name");
    TextField topRepositories = new TextField("Top Repositories");
    TextField oldestForks = new TextField("Oldest Forks");
    /* Action buttons */
    Button searchBtn = new Button
        ("Search", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    HorizontalLayout actions = new HorizontalLayout
                        (searchBtn, cancel);
    Binder<Gitrepository> binder = new Binder<Gitrepository>(Gitrepository.class);
    private Search searchItem;
    private Gitrepository gitrepository;
    private ChangeHandler changeHandler;

    @Autowired
    public SearchEditor(GitrepositoryRepository repository) {
        this.repository = repository;
        add(organizationName, topRepositories, oldestForks, actions);
        // bind using naming convention
        binder.bindInstanceFields(this);
        // Configure and style G
        setSpacing(true);
        searchBtn.getElement().getThemeList().add("primary");
        addKeyPressListener(Key.ENTER, e -> save());
        // wire action buttons to save, delete and reset
        searchBtn.addClickListener(e -> save());
        cancel.addClickListener(e -> editSearch(gitrepository));
        setVisible(false);
    }

    void save() {
        //repository.save(searchItem);
        try {
        String stringUrl = "https://api.github.com/search/repositories?q=user:" + searchItem.getOrganizationName() + "&sort=forks&order=desc&per_page=" + searchItem.getTopRepositories();
        StringBuilder sb = new StringBuilder();
        String line2;
        //Fetch Repos based on Organization
        
        URL url = new URL(stringUrl);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            // for (String line; (line = reader.readLine()) != null;) {
            //     System.out.println(line);
            // }
            
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject json = new JSONObject(tokener);
            JSONArray jsonArray = json.getJSONArray("items"); 

            for (int i = 0; i < jsonArray.length(); i++) {  
            // store each object in JSONObject  
            JSONObject explrObject = jsonArray.getJSONObject(i);  
              
            // get field value from JSONObject using get() method  
            Gitrepository tempRepo = new Gitrepository((String)explrObject.get("name"), (String)explrObject.get("forks"));
            //tempRepo.setGitrepositoryName();
            //tempRepo.setNumberOfForks();
            //System.out.println(explrObject.get("forks"));  
            repository.save(tempRepo);
            }   

            //System.out.println(json);
        } catch(Exception e) {

        }
        } catch(Exception e) {

        }

        changeHandler.onChange();
    }

    public final void editSearch(Gitrepository gitrepository) {
        if (gitrepository == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = gitrepository.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            gitrepository = repository.findById(gitrepository.getId()).get();
        } else {
            gitrepository = gitrepository;
        }
        cancel.setVisible(persisted);
        /* Bind user properties to similarly named fields
         Could also use annotation or "manual binding"
         or programmatically
         moving values from fields to entities before saving*/
        binder.setBean(gitrepository);

        setVisible(true);

        // Focus first name initially
        organizationName.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        /* ChangeHandler is notified when either save or delete
         is clicked*/
        changeHandler = h;
    }

    public interface ChangeHandler {
        void onChange();
    }
}
