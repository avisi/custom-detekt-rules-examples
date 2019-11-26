import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.com.intellij.psi.PsiFile
import org.jetbrains.kotlin.psi.KtNamedFunction

/**
 * This is a sample rule reporting too many functions inside a file.
 */
class TooManyFunctions : Rule() {

    val THRESHOLD = 2


    override val issue = Issue(
        javaClass.simpleName,
        Severity.CodeSmell,
        "This rule reports a file with an excessive function count.",
        Debt.TWENTY_MINS
    )

    private var amount: Int = 0

    override fun visitFile(file: PsiFile) {
        super.visitFile(file)
        if (amount > THRESHOLD) {
            report(
                CodeSmell(
                    issue, Entity.from(file),
                    message = "The file ${file.name} has $amount function declarations. " +
                            "Threshold is specified with $THRESHOLD."
                )
            )
        }
    }

    override fun visitNamedFunction(function: KtNamedFunction) {
        amount++
    }
}

