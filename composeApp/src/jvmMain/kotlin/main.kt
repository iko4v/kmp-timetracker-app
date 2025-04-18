import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension
import org.company.app.App
import org.company.app.di.getSharedModules
import org.jetbrains.compose.reload.DevelopmentEntryPoint
import org.koin.core.context.startKoin

fun main() = application {
    startKoin{
        modules(getSharedModules())
    }
    Window(
        title = "Timetracker Pro",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        DevelopmentEntryPoint {
            App()
        }
    }
}

@Preview
@Composable
fun AppPreview() { App() }
