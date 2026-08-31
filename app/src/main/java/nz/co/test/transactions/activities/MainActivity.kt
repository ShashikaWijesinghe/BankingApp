package nz.co.test.transactions.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import nz.co.test.transactions.ui.navigation.TransactionScreenNavigation
import nz.co.test.transactions.ui.transactiondetails.TransactionDetailsScreen
import nz.co.test.transactions.ui.transactionlist.TransactionListScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = TransactionScreenNavigation.TransactionList.route
            ){
                composable(
                    route = TransactionScreenNavigation.TransactionList.route
                ) {
                    TransactionListScreen { id ->
                        navController.navigate(TransactionScreenNavigation.TransactionDetail.createRoute(id))
                    }
                }

                composable(
                    route = TransactionScreenNavigation.TransactionDetail.route,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) {
                    TransactionDetailsScreen()
                }
            }
        }
    }
}