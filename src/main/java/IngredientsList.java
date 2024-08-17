import java.util.ArrayList;
import java.util.List;

public class IngredientsList {
    private String ingredientPhrase;
    private List <String> ingredients = new ArrayList<>();
    public IngredientsList(String ingredientPhrase, List <String> ingredients) {
        this.ingredientPhrase = ingredientPhrase;
        this.ingredients = ingredients;
    }
    public IngredientsList() {}
    public String getIngredientPhrase() {
        return ingredientPhrase;
    }

    public void setIngredientPhrase(String ingredientPhrase) {
        this.ingredientPhrase = ingredientPhrase;
    }
    public List <String> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List <String> ingredients) {
        this.ingredients = ingredients;
    }
}
