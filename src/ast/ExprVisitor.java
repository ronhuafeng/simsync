// Generated from G:\Tsmart Projects\ParseBIP\src\ast\Expr.g4 by ANTLR 4.1
package ast;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link ExprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface ExprVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link ExprParser#inclusive_or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclusive_or_expression(@NotNull ast.ExprParser.Inclusive_or_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#ObjectExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectExpression(@NotNull ast.ExprParser.ObjectExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#assignment_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_expression(@NotNull ast.ExprParser.Assignment_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#multiplicative_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicative_expression(@NotNull ast.ExprParser.Multiplicative_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#relational_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelational_expression(@NotNull ast.ExprParser.Relational_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#FunctionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(@NotNull ast.ExprParser.FunctionCallContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#exclusive_or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExclusive_or_expression(@NotNull ast.ExprParser.Exclusive_or_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull ast.ExprParser.StatementContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#logical_and_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_and_expression(@NotNull ast.ExprParser.Logical_and_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#additive_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditive_expression(@NotNull ast.ExprParser.Additive_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#if_then_else_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_then_else_expression(@NotNull ast.ExprParser.If_then_else_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#do_action}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDo_action(@NotNull ast.ExprParser.Do_actionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#equality_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEquality_expression(@NotNull ast.ExprParser.Equality_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#primary_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary_expression(@NotNull ast.ExprParser.Primary_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#and_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_expression(@NotNull ast.ExprParser.And_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#unary_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary_expression(@NotNull ast.ExprParser.Unary_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#logical_or_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogical_or_expression(@NotNull ast.ExprParser.Logical_or_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#subtractive_expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubtractive_expression(@NotNull ast.ExprParser.Subtractive_expressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link ExprParser#argument_expression_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument_expression_list(@NotNull ast.ExprParser.Argument_expression_listContext ctx);
}