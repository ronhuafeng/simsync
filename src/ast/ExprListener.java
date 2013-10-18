// Generated from G:\Tsmart Projects\ParseBIP\src\ast\Expr.g4 by ANTLR 4.1
package ast;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExprParser}.
 */
public interface ExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExprParser#inclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void enterInclusive_or_expression(@NotNull ast.ExprParser.Inclusive_or_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#inclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void exitInclusive_or_expression(@NotNull ast.ExprParser.Inclusive_or_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#ObjectExpression}.
	 * @param ctx the parse tree
	 */
	void enterObjectExpression(@NotNull ast.ExprParser.ObjectExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#ObjectExpression}.
	 * @param ctx the parse tree
	 */
	void exitObjectExpression(@NotNull ast.ExprParser.ObjectExpressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#assignment_expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_expression(@NotNull ast.ExprParser.Assignment_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#assignment_expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_expression(@NotNull ast.ExprParser.Assignment_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#multiplicative_expression}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicative_expression(@NotNull ast.ExprParser.Multiplicative_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#multiplicative_expression}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicative_expression(@NotNull ast.ExprParser.Multiplicative_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#relational_expression}.
	 * @param ctx the parse tree
	 */
	void enterRelational_expression(@NotNull ast.ExprParser.Relational_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#relational_expression}.
	 * @param ctx the parse tree
	 */
	void exitRelational_expression(@NotNull ast.ExprParser.Relational_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#FunctionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(@NotNull ast.ExprParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#FunctionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(@NotNull ast.ExprParser.FunctionCallContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#exclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void enterExclusive_or_expression(@NotNull ast.ExprParser.Exclusive_or_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#exclusive_or_expression}.
	 * @param ctx the parse tree
	 */
	void exitExclusive_or_expression(@NotNull ast.ExprParser.Exclusive_or_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(@NotNull ast.ExprParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(@NotNull ast.ExprParser.StatementContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#logical_and_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_and_expression(@NotNull ast.ExprParser.Logical_and_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#logical_and_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_and_expression(@NotNull ast.ExprParser.Logical_and_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#additive_expression}.
	 * @param ctx the parse tree
	 */
	void enterAdditive_expression(@NotNull ast.ExprParser.Additive_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#additive_expression}.
	 * @param ctx the parse tree
	 */
	void exitAdditive_expression(@NotNull ast.ExprParser.Additive_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#if_then_else_expression}.
	 * @param ctx the parse tree
	 */
	void enterIf_then_else_expression(@NotNull ast.ExprParser.If_then_else_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#if_then_else_expression}.
	 * @param ctx the parse tree
	 */
	void exitIf_then_else_expression(@NotNull ast.ExprParser.If_then_else_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#do_action}.
	 * @param ctx the parse tree
	 */
	void enterDo_action(@NotNull ast.ExprParser.Do_actionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#do_action}.
	 * @param ctx the parse tree
	 */
	void exitDo_action(@NotNull ast.ExprParser.Do_actionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#equality_expression}.
	 * @param ctx the parse tree
	 */
	void enterEquality_expression(@NotNull ast.ExprParser.Equality_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#equality_expression}.
	 * @param ctx the parse tree
	 */
	void exitEquality_expression(@NotNull ast.ExprParser.Equality_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#primary_expression}.
	 * @param ctx the parse tree
	 */
	void enterPrimary_expression(@NotNull ast.ExprParser.Primary_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#primary_expression}.
	 * @param ctx the parse tree
	 */
	void exitPrimary_expression(@NotNull ast.ExprParser.Primary_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#and_expression}.
	 * @param ctx the parse tree
	 */
	void enterAnd_expression(@NotNull ast.ExprParser.And_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#and_expression}.
	 * @param ctx the parse tree
	 */
	void exitAnd_expression(@NotNull ast.ExprParser.And_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#unary_expression}.
	 * @param ctx the parse tree
	 */
	void enterUnary_expression(@NotNull ast.ExprParser.Unary_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#unary_expression}.
	 * @param ctx the parse tree
	 */
	void exitUnary_expression(@NotNull ast.ExprParser.Unary_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#logical_or_expression}.
	 * @param ctx the parse tree
	 */
	void enterLogical_or_expression(@NotNull ast.ExprParser.Logical_or_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#logical_or_expression}.
	 * @param ctx the parse tree
	 */
	void exitLogical_or_expression(@NotNull ast.ExprParser.Logical_or_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#subtractive_expression}.
	 * @param ctx the parse tree
	 */
	void enterSubtractive_expression(@NotNull ast.ExprParser.Subtractive_expressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#subtractive_expression}.
	 * @param ctx the parse tree
	 */
	void exitSubtractive_expression(@NotNull ast.ExprParser.Subtractive_expressionContext ctx);

	/**
	 * Enter a parse tree produced by {@link ExprParser#argument_expression_list}.
	 * @param ctx the parse tree
	 */
	void enterArgument_expression_list(@NotNull ast.ExprParser.Argument_expression_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#argument_expression_list}.
	 * @param ctx the parse tree
	 */
	void exitArgument_expression_list(@NotNull ast.ExprParser.Argument_expression_listContext ctx);
}