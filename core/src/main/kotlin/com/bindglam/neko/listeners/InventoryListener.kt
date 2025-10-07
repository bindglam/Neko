package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.CookingRecipe
import org.bukkit.inventory.Recipe
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.inventory.SmithingRecipe
import org.bukkit.inventory.SmithingTransformRecipe
import org.bukkit.inventory.SmithingTrimRecipe
import org.bukkit.inventory.StonecuttingRecipe
import org.bukkit.inventory.TransmuteRecipe

object InventoryListener : Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    fun PrepareItemCraftEvent.cancelCraftItemsWithCustomItems() {
        recipe ?: return
        val recipe = recipe!!

        val useCustomItem = inventory.matrix.any { it != null && NekoProvider.neko().contentManager().customItem(it) != null }
        if(!useCustomItem) return

        if(!checkCustomItemRecipe(recipe))
            inventory.result = null
    }

    fun checkCustomItemRecipe(recipe: Recipe): Boolean {
        return when(recipe) {
            is ShapedRecipe -> !recipe.choiceMap.any { entry -> entry.value is RecipeChoice.MaterialChoice }
            is ShapelessRecipe -> !recipe.choiceList.any { choice -> choice is RecipeChoice.MaterialChoice }
            is TransmuteRecipe -> recipe.input !is RecipeChoice.MaterialChoice && recipe.material !is RecipeChoice.MaterialChoice

            is SmithingTransformRecipe -> recipe.base !is RecipeChoice.MaterialChoice && recipe.addition !is RecipeChoice.MaterialChoice && recipe.template !is RecipeChoice.MaterialChoice
            is SmithingTrimRecipe -> recipe.base !is RecipeChoice.MaterialChoice && recipe.addition !is RecipeChoice.MaterialChoice && recipe.template !is RecipeChoice.MaterialChoice
            is SmithingRecipe -> recipe.base !is RecipeChoice.MaterialChoice && recipe.addition !is RecipeChoice.MaterialChoice

            is StonecuttingRecipe -> recipe.inputChoice !is RecipeChoice.MaterialChoice

            is CookingRecipe<*> -> recipe.inputChoice !is RecipeChoice.MaterialChoice

            else -> true
        }
    }
}