package com.bookora.app.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Home : Screen("home")
    object Categories : Screen("categories")
    object Wishlist : Screen("wishlist")
    object Cart : Screen("cart")
    object Checkout : Screen("checkout")
    object Payment : Screen("payment")
    object OrderSuccess : Screen("order_success")
    object Profile : Screen("profile")
    object BookDetail : Screen("book_detail/{bookId}") {
        fun createRoute(bookId: Int) = "book_detail/$bookId"
    }
}
