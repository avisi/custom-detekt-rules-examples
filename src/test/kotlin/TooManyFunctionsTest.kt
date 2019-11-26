import io.gitlab.arturbosch.detekt.test.assertThat
import io.gitlab.arturbosch.detekt.test.lint
import org.junit.Test

class TooManyFunctionsTest {

    val detektRule = TooManyFunctions()

    @Test
    fun `Two functions is allowed`() {
        detektRule.lint("fun test(){} fun test2(){} ")
        assertThat(detektRule.findings).isEmpty()

    }

    @Test
    fun `Three functions is not allowed`() {
        detektRule.lint("" +
                "class TooManyLines{\n" +
                "    fun test(){}\n" +
                "    fun test2(){}\n" +
                "    fun test3(){}\n" +
                "}\n")
        assertThat(detektRule.findings).isNotEmpty
    }
}
