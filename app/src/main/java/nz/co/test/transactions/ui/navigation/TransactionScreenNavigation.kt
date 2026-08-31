package nz.co.test.transactions.ui.navigation

sealed class TransactionScreenNavigation (val route: String) {
    object TransactionList : TransactionScreenNavigation("TransactionList")
    object TransactionDetail : TransactionScreenNavigation("TransactionDetail/{id}") {
        fun createRoute(id: Int) = "TransactionDetail/$id"
    }
}