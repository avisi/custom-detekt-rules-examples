import io.gitlab.arturbosch.detekt.api.*
import org.jetbrains.kotlin.com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.KtNamedFunction

class NestedApply : Rule() {

    val THRESHOLD = 1

    override val issue = Issue(
        id = javaClass.simpleName,
        severity = Severity.CodeSmell,
        description = "This rule reports nested apply functions",
        debt = Debt.TWENTY_MINS
    )

    override fun visitNamedFunction(function: KtNamedFunction) {
        val nestedApplies = findNumberOfNestedApply(function as PsiElement)
        if (nestedApplies > THRESHOLD) {
            reportError(Entity.from(function))
        }
    }

/**
 * Loops through the AST recursively in order to find the number of nested applies. It counts the number of REFERENCE_EXPRESSION with the text 'apply'.
 * The way the AST is build up; a found apply doenst have another apply as an (indirect) child, but has a direct sibling that instead has child elements.
 */
private fun findNumberOfNestedApply(element: PsiElement, amount: Int = 0): Int {
    val newAmount = if (element.hasApplyChild()) amount + 1 else amount

    return element.children.map {
        findNumberOfNestedApply(it, newAmount)
    }.max() ?: newAmount
}

    private fun PsiElement.hasApplyChild(): Boolean =
        this.children.any { it.toString() == "REFERENCE_EXPRESSION" && it.text == "apply" }

    private fun reportError(entity: Entity) = report(
        CodeSmell(
            issue = issue,
            entity = entity,
            message = "Nested applies reduce readability " +
                    "There is a maximum of $THRESHOLD apply functions."
        )
    )

}
