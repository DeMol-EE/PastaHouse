/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Warkst
 */
public class Recipe extends Component {
    private final String table_id = Configuration.center().getDB_TABLE_REC();
    
    private String description;
    private ArrayList<Component> components;
    private ArrayList<BasicIngredient> basicIngredients;
    private ArrayList<Recipe> recipes;
    
    public Recipe(int id, String name){
	super(id, name);
    }
    
    private Recipe(int id, String name, String description){
	super(id, name);
	this.description = description;
    }
    
    @Override
    public void create() throws SQLException{
	Database.driver().executeInsert(table_id, "description = "+description);
    }
    
    @Override
    public void update() throws SQLException{
	Database.driver().executeUpdate(table_id, super.getId(), "description = "+description);
	// update recipes-recipes
	// update recipes-ingredients
    }
    
    @Override
    public void delete(){
	
    }
}
