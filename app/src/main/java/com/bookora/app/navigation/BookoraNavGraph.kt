package com.bookora.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bookora.app.data.repository.BookRepository
import com.bookora.app.ui.screens.BookDetailScreen
import com.bookora.app.ui.screens.CartScreen
import com.bookora.app.ui.screens.CategoriesScreen
import com.bookora.app.ui.screens.CheckoutScreen
import com.bookora.app.ui.screens.HomeScreen
import com.bookora.app.ui.screens.LoginScreen
import com.bookora.app.ui.screens.OnboardingScreen
import com.bookora.app.ui.screens.OrderSuccessScreen
import com.bookora.app.ui.screens.PaymentScreen

@Composable
fun BookoraNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding.route
    ) {
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onSignUpClick = {}
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onBookClick = { bookId ->
                    navController.navigate(Screen.BookDetail.createRoute(bookId))
                },
                onCategoriesClick = {
                    navController.navigate(Screen.Categories.route)
                },
                onCartClick = {
                    navController.navigate(Screen.Cart.route)
                }
            )
        }

        composable(Screen.Categories.route) {
            CategoriesScreen(
                onBack = { navController.popBackStack() },
                onCartClick = { navController.navigate(Screen.Cart.route) }
            )
        }

        composable(Screen.Cart.route) {
            CartScreen(
                onBack = { navController.popBackStack() },
                onCheckoutClick = { navController.navigate(Screen.Checkout.route) }
            )
        }

        composable(Screen.Checkout.route) {
            CheckoutScreen(
                onBack = { navController.popBackStack() },
                onProceedToPayment = { navController.navigate(Screen.Payment.route) }
            )
        }

        composable(Screen.Payment.route) {
            PaymentScreen(
                onBack = { navController.popBackStack() },
                onPaySuccess = {
                    navController.navigate(Screen.OrderSuccess.route) {
                        popUpTo(Screen.Cart.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.OrderSuccess.route) {
            OrderSuccessScreen(
                onContinueShopping = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onViewOrders = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.BookDetail.route,
            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getInt("bookId") ?: return@composable
            val book = BookRepository.allBooks.find { it.id == bookId }
            book?.let {
                BookDetailScreen(
                    book = it,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
