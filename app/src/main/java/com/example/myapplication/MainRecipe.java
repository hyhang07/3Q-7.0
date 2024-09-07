package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainRecipe extends AppCompatActivity {

    RecyclerView myrecyclerView;
    RecycleViewAdapter myAdapter;
    List<FoodRecipe> recipes1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipe);

        recipes1 = new ArrayList<>();
        recipes1.add(new FoodRecipe("Chicken on Sweetcorn Puree", "a) 4 large (200g each) corn cobs\n\n" + "b) 4 x 150g boneless, skinless chicken breast halves\n\n" + "c) 2 tablespoons (20g) coriander seeds, ground\n\n" + "d) 1 punnet (250g) cherry tomatoes",
                "Method", "1. Remove the kernels from the corn by carefully cutting downwards.\n\n" + "2. Steam the corn and puree with a little of the water from the steamer.\n\n" +
                "3. In a nonstick frying pan, add 2 tablespoons of water and cook the chicken over moderate / high heat for 4 to 5 minutes each side, or until cooked through.\n\n" + "4. In the last minute, add the coriander seeds and cracked pepper, turning to completely coat the chicken. Set aside to rest.\n\n" +
                "5. Add the tomatoes and sauté until just softened.\n\n" + "6. Serve the chicken on the sweet corn puree and top with sautéed tomatoes.\n", R.drawable.img_2));

        recipes1.add(new FoodRecipe("Simple Lemon Pepper Salmon", "a) 1 salmon fillet, skin off\n\n" + "b) Olive oil spray\n\n" + "c) ½ lemon\n\n" + "d) Cracked pepper",
                "Method", "1. Prepare a small baking dish with alfoil, using enough foil to create a pocket around the fish.\n\n" + "2. Lightly spray foil and salmon with olive oil.\n\n" + "3. Cut a few slices of lemon, then juice the remainder.\n\n" +
                 "4. Pour lemon juice around the salmon, cover with cracked pepper to your taste and top with lemon slices.\n\n" + "5. Pour lemon juice around the salmon, cover with cracked pepper to your taste and top with lemon slices.\n\n" +
                 "6. Bake at 180°C for 20 minutes for medium to well done.\n\n" + "7. Serve with mashed low GI potato mash (or try cauliflower mash)  and steamed vegetables.\n\n", R.drawable.img_3));

        recipes1.add(new FoodRecipe("Sweet n Sour Chicken", "a) 2 tablespoons of olive oil\n\n" + "b) 500g of trimmed chicken, diced into cubes\n\n" +
                 "c) 4 cups of raw mixed vegetables, eg onion, carrot, celery, capsicum, green beans, snow peas, corn, bok choy (whatever you have) cut into cubes.\n\n" + "d) 1 teaspoon of salt reduced stock powder\n\n" + "e) 1 cup of water\n\n" + "f) 2 tablespoons of vinegar\n\n" +
                 "g) 2 teaspoons of brown sugar\n\n" + "h) 2 tablespoons of salt reduced tomato sauce\n\n" + "i) ½ cup of pineapple pieces, peeled if fresh or drain the juice if using canned pineapple.\n\n" + "j) 2 tablespoons of cornflour\n\n" + "k) 3 cups of cooked brown basmati rice",
                 "Method", "1. Heat oil in large frying pan\n\n" + "2. Add chicken and cook until brown\n\n" + "3. Reduce heat to medium and add the mixed vegetables. Continue to cook for 5 more minutes\n\n" + "4. Reduce heat to low and add in the chicken stock powder, water, vinegar, brown sugar and tomato sauce and simmer for 5 minutes\n\n" +
                 "5. Add in the pineapple. In a separate bowl mix cornflour and two tablespoons of extra water and mix until a smooth paste. Add to frying pan\n\n" +
                 "6. Mix everything together until cornflour goes clear and sauce thickens to desired consistency\n\n" + "7. Serve with low GI brown basmati rice.\n\n", R.drawable.img_4));

        recipes1.add(new FoodRecipe("Lemon, Tarragon and Poached Chicken Sandwitch", "a) 150g chicken breast or thigh filet, fat trimmed \n\n" +
                 "b) 50g light sour cream or crème fraiche\n\n" + "c) 2 tablespoons fresh tarragon, finely chopped (use basil if tarragon not available)\n\n" + "d) 1 lemon, finely zested \n\n" + "e) 20g toasted chopped walnuts \n\n" + "f) 4 slices low GI seed or multigrain bread \n\n" +
                 "g) Salad to serve (greens, sliced tomato, cucumber)", "Method", "1. Place chicken in a small pan and cover with cold water. Bring to the boil over medium heat \n\n" + "2. Turn heat down to low and gently simmer for another 5-7 minutes \n\n" +
                 "3. When chicken is poached through (slice in a thick area and check flesh is no longer raw if unsure) remove from water and allow to cool for 5 minutes \n\n" +
                 "4. Then shred chicken by tearing thin strips off and place in a bowl \n\n" + "5. In the bowl, add sour cream, tarragon, lemon zest and walnuts and mix well (don’t skimp on the lemon zest!) \n\n" +
                 "6. Spoon onto bread, add salad and serve\n\n", R.drawable.img_5));

        recipes1.add(new FoodRecipe("Easy Chicken and Vegetable Soup", "a) 1 teaspoon extra virgin olive oil \n\n" + "b) 1 medium brown onion, chopped \n\n" + "c) 2 medium carrots, chopped or sliced \n\n" + "d) 2 medium stalks of celery, chopped \n\n" +
                 "e) 2 medium chicken breasts, cooked and chopped or shredded \n\n" + "f) 2 medium potatoes, cut into cubes \n\n" + "g) 2 cups of reduced salt vegetable stock \n\n" + "h) 2 cups of water \n\n" + "i) ½ cup frozen green peas \n\n" + "j) 1 grind of black pepper \n\n" + "k) 1 tablespoon chopped parsley",
                 "Method", "1. Fry the chopped onion in the oil in a large (about 3L) pot until transparent but not browned. \n\n" + "2. Add the carrots, celery, chicken, potatoes, stock and water; bring to the boil and simmer for about 20 minutes. \n\n" +
                 "3. Add the pepper, to taste, the frozen peas and simmer until the peas are cooked and still bright green. \n\n" + "4. Serve in bowls with parsley sprinkled over.\n\n", R.drawable.img_6));

        recipes1.add(new FoodRecipe("Teriyaki Salmon", "a) 1 fillet (skin on)\n\n" + "b) ½ teaspoon ground black pepper\n\n" + "c) 1/2 tablespoon plain flour" + "d) ½ tablespoon sake\n\n" + "e) ½ tablespoon mirin\n\n" + "f) ½ tablespoon sugar\n\n" + "g) 1 tablespoon soy sauce (reduced salt)",
                 "Method", "1. Heat the oven to 180°C and line a baking tray with baking paper.\n\n" + "2. Combine all of the sauce ingredients and mix well until sugar is dissolved. If sugar is not dissolving, mircrowave the sauce mixture for 30 seconds and stir until sugar dissolved.\n\n" +
                 "3. Season salmon with black pepper on both sides. Then coat the salmon with flour.\n\n" + "4. Once salmon is fully coated with flour, place the salmon on the lined baking tray and bake it for about 10 minutes or until it turns slightly golden brown.\n\n" +
                 "5. Add the sauce into the bake tray and continue baking it for about 10 minutes or until the sauce is boiling.\n\n" + "6. Once sauce is boiling turn the salmon over to make sure all sides are coated with the sauce. By now the sauce should have thickened. It is ready to be served.\n\n", R.drawable.img_7));

        recipes1.add(new FoodRecipe("Low Carb Spaghetti Marinara", "a) 1 x 125g pack of Zucchini noodles or 125g of zucchini to make your own (see note below)\n\n" + "b) 1 tablespoon extra virgin olive oil\n\n" + "c) 1 small red onion, chopped\n\n" + "d) 700g cherry tomatoes\n\n" +
                 "e) 4 cloves of garlic, peeled and crushed\n\n" + "f) 750g seafood marinara mix\n\n" + "g) Zest of 1 lemon\n\n" + "h) Juice of 2 lemons, about  5 tablespoons\n\n" + "i) ½ cup chopped parsley\n\n" + "j) 120g rocket\n\n" + "k) 1 tablespoon pine nuts\n\n" + "l) Black pepper", "Method", "1. Heat oil over a medium heat in a large frying pan.\n\n" +
                 "2. Cook the onion, stirring, until softened. Reduce heat to low and add the tomatoes and garlic.\n\n" + "3.Cook until the tomatoes start to collapse. Add the marinara mix and cook, stirring occasionally, for 5 minutes or until seafood is cooked.\n\n" + "4. Heat zucchini noodles according to instructions on the packet or make your own.\n\n" +
                 "5. Add zucchini noodles to the seafood mixture with lemon zest, lemon juice, parsley and rocket. Toss until combined. Season with pepper. Sprinkle with pine nuts.\n\n", R.drawable.img_9));

        recipes1.add(new FoodRecipe("Kangaroo Spaghetti Bolognese", "a) 1 tablespoon extra virgin olive oil\n\n" + "b) 1 brown onion, diced\n\n" + "c) 2 garlic cloves, crushed\n\n" + "d) 1kg kangaroo mince\n\n" + "e) 200g mushrooms, sliced\n\n" + "f) 3 tablespoon dried Italian herbs\n\n" + "g) Pepper to taste\n\n" + "1 celery stick, diced\n\n" + "h) 1 zucchini, diced\n\n" +
                 "i) 1 carrot, diced\n\n" + "j) 800g canned crushed tomatoes\n\n" + "k) ¼ cup tomato paste\n\n" + "l) 250mL reduced salt stock\n\n" + "m) 500g pasta spirals or whatever shape you prefer\n\n" + "n) Salad leaves", "Method", "1. Prepare vegies.\n\n" + "2. Heat oil in a large frying pan.\n\n" +
                 "3. Add onion, garlic, carrot, celery, mushrooms, zucchini and cook on a medium heat for five or so minutes. Cook until the onion is soft.\n\n" + "4. Increase the heat to high and add the mince. Cook for five more minutes or until the meat is browned and cooked through.\n\n" + "5. Add the tomatoes, tomato paste, stock and herbs. Stir to combine well and bring to a simmer.\n\n" +
                 "6. Cook pasta as per the packet instructions till it is al dente (firm to the bite – this has a lower GI than soggy pasta).\n\n" + "7. Serve with salad leaves.\n\n", R.drawable.img_11 ));

        recipes1.add(new FoodRecipe("Spaghetti with Poached Egg, Fresh Salmon", "a) 6 eggs\n\n" + "b) 400g wholemeal spaghetti\n\n" + "c) 300g fresh salmon, thinly sliced\n\n" + "d) 1½ cups baby spinach\n\n" + "e) 80 ml extra virgin olive oil\n\n" + "f) 1 lemon, zest and juice\n\n" + "g) Pepper to taste\n\n" + "h) 1/4 cup parmesan shavings", "Method", "1. Cook the spaghetti in a large pot of water.\n\n" +
                 "2. Poach the eggs and set aside.\n\n" + "3. Place salmon and spinach into a large bowl.\n\n" + "4. Once the pasta is cooked lift it out of the water into the bowl stirring carefully. Add a little of the pasta water to bring it all together. The heat from the pasta and water will cook the salmon and spinach.\n\n" + "5. Combine oil, lemon zest and juice, drizzle over the pasta, season with pepper.\n\n" +
                 "6. Place onto plates and top with the poached egg and some parmesan shavings.\n\n", R.drawable.img_13));

        recipes1.add(new FoodRecipe("Aubergine and Cheese Pizza", "a) 2 eggplants (630g) cut length ways into 2cm thick steaks\n\n" + "b) 100g grated mozzarella\n\n" + "c) 4 Roma tomatoes (350g), sliced\n\n" + "d) ½ cup (30g) basil leaves", "Method", "1. Preheat oven to 1800C.\n\n" + "2. Line baking tray with baking paper\n\n" + "3. Lay the eggplant steaks onto the tray and season with sea salt\n\n" +
                 "4. Bake for 20 minutes or until softened.\n\n" + "5. Onto the eggplant sprinkle with cheese, layer with tomatoes and season.\n\n" + "6. Return to the oven for 5-7 minutes or until cheese is melting and is golden and bubbling.\n\n" + "7. Serve scattered with fresh basil leaves.\n\n", R.drawable.img_10));

        recipes1.add(new FoodRecipe("Nectarine and Yoghurt Slice", "a) 150g butter (unsalted)\n\n" + "b) 2 eggs (at room temperature)\n\n" + "c) ½ cup of caster sugar\n\n" + "d) 1½ cup wholemeal self raising flour\n\n" + "e) ¼ teaspoon bicarbonate of soda (bicarb soda)\n\n" + "f) 1 teaspoon vanilla essence\n\n" + "g) 1 teaspoon ground cinnamon\n\n" + "h) 1 cup plain greek yoghurt\n\n" + "i) ½ cup almonds, chopped\n\n" +
                 "j) 4 fresh nectarines, stones removed and sliced into quarters", "Method", "1. Preheat oven to 180C. Line baking tray 20x30cm with baking paper\n\n" + "2. Blend butter and sugar in blender until white. Add in eggs one at a time\n\n" + "3. Sift flour with bicarb and cinnamon\n\n" + "4. Fold yoghurt and flour through egg mixture. Add in vanilla essence. Fold through ¾ fruit and all nuts gently.\n\n" +
                 "5. Spoon mix into tray and flatten until smooth. Top with remaining fruit pieces.\n\n" + "6. Bake for 20-25 minutes until skewer or knife comes out clean. Leave in tray for 5 minutes before transferring to cooling rack. Remove paper to serve.\n\n", R.drawable.img_12));

        recipes1.add(new FoodRecipe("Best Chocolate Cupcake", "a) 1½ cups all purpose (plain flour)\n\n" + "b) 1¼ teaspoon baking soda\n\n" + "c) ½ teaspoon salt\n\n" + "d) ½ cup cocoa powder, sifted\n\n" + "e) 1 tablespoon instant coffee granules\n\n" + "f) ¾ cup milk, hot\n\n" + "g) ½ cup oil\n\n" + "h) 1 cup brown sugar\n\n" + "i) 1 teaspoon vanilla extract\n\n" + "j) ¾ cup sour cream, light\n\n" + "k) 1 large egg",
                 "Method", "1. Preheat oven to 210°C. If you have heart shaped cupcake trays, line with cupcake paper cases. Alteratively use normal cupcake cases.\n\n" + "2. Sift flour, baking soda and salt into a large bowl and mix them together.\n\n" + "3. In a separate bowl, sift cocoa powder then add coffee and hot milk. Whisk until the mixture has no lumps.\n\n" +
                 "4. Add sugar, oil, egg, sour cream and vanilla to the cocoa mixture and whisk until smooth.\n\n" + "5. Pour the wet mixture into the flour mixture bowl. Whisk until smooth and stop once it the batter is smooth and glossy.\n\n" + "6. Fill the cupcake hole with the batter until it is ¾ full to prevent an overflow when baking.\n\n" +
                 "7. Bake at 210°C for 5 mintues. Then turn down to 190°C for 15 minutes. Use a toothpick to check if the cupcake is ready; the toothpick will come out clean when they are cooked.\n\n" + "8. Rest in the cupcakes tin for 5 minutes, then transfer to cooling rack.\n\n" + "9. Allow to cool for about 15 minutes. Enjoy.\n\n", R.drawable.img_8));

        myrecyclerView = (RecyclerView) findViewById(R.id.recycleView_id);

        myAdapter = new RecycleViewAdapter(this, recipes1);

        myrecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        myrecyclerView.setAdapter(myAdapter);
    }
}