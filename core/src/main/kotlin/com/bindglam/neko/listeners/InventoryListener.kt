package com.bindglam.neko.listeners

import com.bindglam.neko.api.NekoProvider
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.PrepareItemCraftEvent
import org.bukkit.inventory.CookingRecipe
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.ShapedRecipe
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.inventory.SmithingRecipe
import org.bukkit.inventory.SmithingTransformRecipe
import org.bukkit.inventory.SmithingTrimRecipe
import org.bukkit.inventory.StonecuttingRecipe
import org.bukkit.inventory.TransmuteRecipe

class InventoryListener : Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    fun PrepareItemCraftEvent.cancelCraftItemsWithCustomItems() {
        recipe ?: return
        val recipe = recipe!!

        val useCustomItem = inventory.matrix.any { it != null && NekoProvider.neko().contentManager().customItem(it) != null }
        if(!useCustomItem) return

        when(recipe) {
            is ShapedRecipe -> recipe.choiceMap.forEach { ch, choice -> if(choice is RecipeChoice.MaterialChoice) inventory.result = null }
            is ShapelessRecipe -> recipe.choiceList.forEach { choice -> if(choice is RecipeChoice.MaterialChoice) inventory.result = null }
            is TransmuteRecipe -> if(recipe.input is RecipeChoice.MaterialChoice || recipe.material is RecipeChoice.MaterialChoice) inventory.result = null

            is SmithingTransformRecipe -> if(recipe.base is RecipeChoice.MaterialChoice || recipe.addition is RecipeChoice.MaterialChoice || recipe.template is RecipeChoice.MaterialChoice) inventory.result = null
            is SmithingTrimRecipe -> if(recipe.base is RecipeChoice.MaterialChoice || recipe.addition is RecipeChoice.MaterialChoice || recipe.template is RecipeChoice.MaterialChoice) inventory.result = null
            is SmithingRecipe -> if(recipe.base is RecipeChoice.MaterialChoice || recipe.addition is RecipeChoice.MaterialChoice) inventory.result = null

            is StonecuttingRecipe -> if(recipe.inputChoice is RecipeChoice.MaterialChoice) inventory.result = null

            is CookingRecipe<*> -> if(recipe.inputChoice is RecipeChoice.MaterialChoice) inventory.result = null
        }
    }
}