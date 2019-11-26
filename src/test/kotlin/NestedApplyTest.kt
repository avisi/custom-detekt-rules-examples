import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test
import java.io.File

class NestedApplyTest {

    val subject = NestedApply()
    val resourceLocation: String = "src/test/resources/nestedApplyResources"


    @Test
    fun `can have seperate applies`() {
        val fileContent = getFileContents("$resourceLocation/SeperateApplyResource.kt")
        subject.lint(fileContent)
        assertThat(subject.findings).isEmpty()

    }

    @Test
    fun `cant nest applies`() {
        val fileContent = getFileContents("$resourceLocation/NestedApplyResource.kt")
        subject.lint(fileContent)
        assertThat(subject.findings).isNotEmpty
    }

    private fun getFileContents(path: String) = File(path).bufferedReader().readLines().joinToString("")
}
