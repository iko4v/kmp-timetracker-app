import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import org.company.app.App
import kotlinx.browser.document
import org.company.app.di.getSharedModules
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin{
        modules(getSharedModules())
    }
    val body = document.body ?: return
    ComposeViewport(body) {
        App()
    }
}
