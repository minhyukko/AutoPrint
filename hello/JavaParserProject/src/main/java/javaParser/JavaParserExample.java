package javaParser;

import com.github.javaparser.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.body.MethodDeclaration;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.VariableDeclarator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JavaParserExample {
	
	int q = 0;
	
	public JavaParserExample() {
		q = 5;
	}

	/**
	 * 
	 * @param args The file path
	 */
    public static int main(String[] args) {
        // Specify the path to the Java source code file
        String filePath = args[args.length - 1];
        System.out.println(filePath);

        
        try {
            // Create a JavaParser instance
            JavaParser javaParser = new JavaParser();

            // Load the Java source code file
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);

            // Parse the Java source code
            CompilationUnit compilationUnit = javaParser.parse(fis).getResult().orElseThrow();

            // Generate Abstract Syntax Tree
            ParserConfiguration parserConfiguration = new ParserConfiguration();

            VoidVisitorAdapter<Void> visitor = new VoidVisitorAdapter<>() {
                @Override
                public void visit(MethodDeclaration methodDeclaration, Void arg) {
                    super.visit(methodDeclaration, arg);
                }
            };

// Parse the Java file
            ParseResult<CompilationUnit> parseResult = new JavaParser().parse(new File(filePath));

            // Retrieve the CompilationUnit from the ParseResult
            if (parseResult.isSuccessful()) {

                // Find all field declarations
                List<FieldDeclaration> fieldDeclarations = compilationUnit.findAll(FieldDeclaration.class);

                // Set to track variable names already printed
                Set<String> printedVariableNames = new HashSet<>();

                // Print variable names from field declarations
                for (FieldDeclaration fieldDeclaration : fieldDeclarations) {
                    List<VariableDeclarator> variableDeclarators = fieldDeclaration.getVariables();
                    for (VariableDeclarator variableDeclarator : variableDeclarators) {
                        String variableName = variableDeclarator.getNameAsString();
                        if (!printedVariableNames.contains(variableName)) {
                            printedVariableNames.add(variableName);
                        }
                    }
                }

                // Visit the AST using the visitor
                visitor.visit(compilationUnit, null);

                // Visitor to extract variable names from method bodies
                VoidVisitorAdapter<Void> methodVisitor = new VoidVisitorAdapter<Void>() {
                    @Override
                    public void visit(VariableDeclarator variableDeclarator, Void arg) {
                        String variableName = variableDeclarator.getNameAsString();
                        if (!printedVariableNames.contains(variableName)) {
                            printedVariableNames.add(variableName);

                            // Insert print statement after the variable declaration

                        }
                        super.visit(variableDeclarator, arg);
                    }
                };

                // Visit all cd in the compilation unit to extract variable names
                methodVisitor.visit(compilationUnit, null);


            } else {
                // Handle parse errors if necessary
                List<Problem> parseErrors = parseResult.getProblems();
                for (Problem error : parseErrors) {
                    System.err.println("Parse error: " + error.getMessage());
                }
                return -1;
            }


//            visitor.visit(compilationUnit, null);

            // Finds when method is declared
            compilationUnit.findAll(MethodDeclaration.class).forEach(methodDeclaration -> {
                // Add a print statement to each method
                methodDeclaration.getBody().ifPresent(body -> {
                    String methodName = methodDeclaration.getName().asString();
                    if (body.getStatements().size() > 0 && body.getStatement(body.getStatements().size() - 1).isReturnStmt()) { // Adds the method completion print statement before the return statement if return statement is present in the method.
                        body.addStatement(body.getStatements().size() - 1, StaticJavaParser.parseStatement("System.out.println(\"Method " + methodName + " successfully completed.\");"));
                    } else {
                        body.addStatement(StaticJavaParser.parseStatement("System.out.println(\"Method " + methodName + " successfully completed.\");"));
                    }
                });


            });

            List<Integer> assignExprLineNum1 = new LinkedList<Integer>(); // An ArrayList of line numbers where the variable assignment occurs.
            List<String> assignExprVarName1 = new LinkedList<String>(); // An ArrayList of variable names to be printed.
            //Finds when variable is assigned.
            compilationUnit.findAll(AssignExpr.class).forEach(variableDeclarator -> {
                assignExprLineNum1.add(variableDeclarator.getRange().get().begin.line);
                assignExprVarName1.add(variableDeclarator.getTarget().toString());
            });

            System.out.println(compilationUnit);
            compilationUnit.findAll(VariableDeclarationExpr.class).forEach(variableDeclarator -> {
//                if (variableDeclarator.getChildNodes().get(0))
//                System.out.println(variableDeclarator.getChildNodes().get(0).getChildNodes().get(2).getLineEndingStyle());
//                System.out.println(variableDeclarator.getVariables().get(0).getChildNodes().get(2).getClass().getName().equals("com.github.javaparser.ast.expr.MethodCallExpr") || variableDeclarator.getVariables().get(0).getChildNodes().get(2).getClass().getName().equals("com.github.javaparser.ast.expr.CastExpr"));
//                System.out.println(variableDeclarator.getRange().get().begin.line);
//                System.out.println(variableDeclarator.getVariable(0).getName().asString());

//                variableDeclarator.getChildNodes().get(2).getType();
//                System.out.println(variableDeclarator.getVariable(0).getInitializer());
                for (int i = 0; i < assignExprLineNum1.size(); i++) {
                    if (assignExprLineNum1.get(i) > variableDeclarator.getRange().get().begin.line) {
                        assignExprLineNum1.add(i, variableDeclarator.getRange().get().begin.line);
                        assignExprVarName1.add(i, variableDeclarator.getVariable(0).getName().asString());
                        break;
                    }
                }
//                assignExprLineNum1.add(variableDeclarator.getRange().get().begin.line);
//                assignExprVarName1.add(variableDeclarator.getVariable(0).getName().asString());
//                assignExprVarName.add(variableDeclarator.getTarget().toString());
            });

            Queue<Integer> assignExprLineNum = new LinkedList<>(assignExprLineNum1);
            Queue<String> assignExprVarName = new LinkedList<>(assignExprVarName1);

            List<ConstructorDeclaration> c = compilationUnit.findAll(ConstructorDeclaration.class);
            // Retrieve the list of lines in the compilation unit
            List<MethodDeclaration> method = compilationUnit.findAll(MethodDeclaration.class);


            List<CallableDeclaration> cd = new ArrayList<CallableDeclaration>();
            cd.addAll(c);
            cd.addAll(method);

            cd = cdSort(cd);

            Statement codeSnippet = new ExpressionStmt(new NameExpr("System.out.println(" + "variable" + ")"));

            // Find the method declaration at the target line
            for (int i = 0; i < cd.size(); i++) {
                CallableDeclaration targetMethod = cd.get(i); // Example: Get the first method declaration
                BlockStmt methodBody = null;
                if (cd.get(i).isConstructorDeclaration()) {
                    ConstructorDeclaration targetMethod1 = (ConstructorDeclaration) cd.get(i); // Example: Get the first method declaration
                    if (!targetMethod1.getBody().isEmpty())
                        methodBody = targetMethod1.getBody();
                } else if (cd.get(i).isMethodDeclaration()) {
                    MethodDeclaration targetMethod1 = (MethodDeclaration) cd.get(i); // Example: Get the first method declaration
                    if (!targetMethod1.getBody().isEmpty())
                        methodBody = targetMethod1.getBody().orElseThrow();
                }

                // Checks whether the print statement that is about to be inserted is within the range of the method or not.
                while (assignExprLineNum.size() > 0 && assignExprLineNum.peek() >= targetMethod.getRange().get().begin.line && assignExprLineNum.peek() <= targetMethod.getRange().get().end.line) {
                    parseStatements(targetMethod, methodBody, assignExprLineNum, assignExprVarName, codeSnippet, args);
                }
            }

            // Print the modified source code
//            System.out.println(compilationUnit.toString());

            // Can handle increment/decrement operations.
            compilationUnit.findAll(UnaryExpr.class).forEach(variableDeclarator -> {
            });

            compilationUnit.findAll(FieldDeclaration.class).stream()
                    .filter(f -> f.isPublic() && f.isStatic())
                    .forEach(f -> System.out.println("Check static field at line " + f.getRange().map(r -> r.begin.line).orElse(-1)));

            MethodDeclaration toStringMethod = new MethodDeclaration()
                    .setModifiers(Modifier.Keyword.PUBLIC)
                    .setType("String")
                    .setName("toString")
                    .addAnnotation(Override.class);
            BlockStmt body = new BlockStmt()
                    .addStatement("return \"Your custom implementation of toString() here\";");
            toStringMethod.setBody(body);
            compilationUnit.getClassByName("Main")
                    .ifPresent(classDeclaration -> classDeclaration.addMember(toStringMethod));


// Generate modified code from the modified AST
            String modifiedCode = compilationUnit.toString();
            System.out.println(modifiedCode);

            //Writes the content to the file.
            Path path = Paths.get(filePath);
            Files.writeString(path, modifiedCode, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
		return 0;
    }

    public static void parseStatements(CallableDeclaration targetMethod, BlockStmt methodBody, Queue<Integer> assignExprLineNum, Queue<String> assignExprVarName, Statement codeSnippet, String[] args) {
        for (int j = 0; j < methodBody.getStatements().size(); j++) {
            if (assignExprLineNum.size() != 0 && assignExprLineNum.peek() <= methodBody.getRange().get().begin.line) { // Checks whether the value being printed is in the statement declaration or not/
            	if (assignExprLineNum.peek() > Integer.parseInt(args[0]) && assignExprLineNum.peek() < Integer.parseInt(args[1]) && (Arrays.asList(args).contains(assignExprVarName.peek()) || args[2].equals(""))) // args[2].equals("") would mean that the "Variables to Print" section is empty.
            		methodBody.getStatements().add(++j, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.peek() + ")")));
                assignExprLineNum.poll();
                assignExprVarName.poll();
//                methodBody.getStatements().add(++j, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.poll() + ")")));
//                assignExprLineNum.poll();
            }
            if (assignExprLineNum.size() > 0 && !methodBody.getStatement(j).getRange().isEmpty() && assignExprLineNum.peek() >= methodBody.getStatement(j).getRange().get().begin.line && assignExprLineNum.peek() <= methodBody.getStatement(j).getRange().get().end.line) {
                if (methodBody.getStatement(j).isTryStmt()) {
                    TryStmt ts = (TryStmt) methodBody.getStatement(j);
                    BlockStmt bs = ts.getTryBlock();
                    // A recursive method?
                    parseStatements(targetMethod, bs, assignExprLineNum, assignExprVarName, codeSnippet, args);
                    if (ts.getCatchClauses().size() > 0) { // 1 or more catch block exists
                        NodeList<CatchClause> catchClauses = ts.getCatchClauses();
                        for (CatchClause cc: ts.getCatchClauses()) {
                            BlockStmt bc = cc.getBody();
                            parseStatements(targetMethod, bc, assignExprLineNum, assignExprVarName, codeSnippet, args);
                        }
                    }
                } else if (methodBody.getStatement(j).isWhileStmt()) {
                    BlockStmt bs = (BlockStmt) methodBody.getStatement(j).getChildNodes().get(1);
                    if (methodBody.getStatement(j).getRange().get().begin.line == assignExprLineNum.peek()) {
                    	if (assignExprLineNum.peek() > Integer.parseInt(args[0]) && assignExprLineNum.peek() < Integer.parseInt(args[1]) && (Arrays.asList(args).contains(assignExprVarName.peek()) || args[2].equals(""))) // args[2].equals("") would mean that the "Variables to Print" section is empty.
                    		methodBody.getStatements().add(0, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.peek() + ")")));
                        assignExprLineNum.poll();
                        assignExprVarName.poll();
//                        bs.getStatements().add(0, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.poll() + ")")));
//                        assignExprLineNum.poll();
                    }
                    parseStatements(targetMethod, bs, assignExprLineNum, assignExprVarName, codeSnippet, args);
                } else if (methodBody.getStatement(j).isForStmt()) {
                    ForStmt fs = (ForStmt) methodBody.getStatement(j);
                    if (fs.getBody().isExpressionStmt()) { // Handles the case of one line if statement
                        if (assignExprLineNum.peek() <= fs.getBody().getRange().get().begin.line) {
                            if (assignExprVarName.peek().contains("[")) { // Checks whether an entire array has to be printed or not.
                                int index = assignExprVarName.peek().indexOf('[');
                                // Retrieve the substring until "["
                                String substring = assignExprVarName.poll().substring(0, index);
                                if (assignExprLineNum.peek() > Integer.parseInt(args[0]) && assignExprLineNum.peek() < Integer.parseInt(args[1]) && (Arrays.asList(args).contains(assignExprVarName.peek()) || args[2].equals(""))) // args[2].equals("") would mean that the "Variables to Print" section is empty.
                            		methodBody.getStatements().add(++j, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + substring + ": \" + Arrays.toString(" + substring + "))")));
//                                methodBody.getStatements().add(++j, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + substring + ": \" + Arrays.toString(" + substring + "))")));
                            } else {
                            	if (assignExprLineNum.peek() > Integer.parseInt(args[0]) && assignExprLineNum.peek() < Integer.parseInt(args[1]) && (Arrays.asList(args).contains(assignExprVarName.peek()) || args[2].equals(""))) // args[2].equals("") would mean that the "Variables to Print" section is empty.
                            		methodBody.getStatements().add(++j, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.peek() + ")")));
                                assignExprVarName.poll();
//                                methodBody.getStatements().add(++j, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.poll() + ")")));
                            }
                            assignExprLineNum.poll();
                        }
                    } else {
                        Statement s = fs.getBody();
                        NodeList<Statement> statements = new NodeList<>();
                        statements.add(s);
                        BlockStmt bs = new BlockStmt();


                        if (fs.isForStmt() || fs.isIfStmt()) {
                            if (statements.size() > 0 && statements.get(0).toString().substring(0, 3).equals("{\r\n")) { // getStatement(0).toString().substring(0, 1).equals("{\r\n")
                                BlockStmt bstmt = (BlockStmt) statements.get(0);
                                bs.setStatements(bstmt.getStatements());
                            } else {
                                bs.setStatements(statements);
                            }
                            if (bs.getStatements().size() == 1) { // We modify the range only when bs has one statement contained because in this case, we can guarantee the parent's range is equal to the child's range.
                                bs.getStatement(0).setRange(s.getRange().get());
                            }
                        } else {
                            for (Node node : s.getChildNodes()) {
                                String str = node.toString();
                                bs.addStatement(str);
                            }
                        }
                        bs.setRange(s.getRange().get());
                        parseStatements(targetMethod, bs, assignExprLineNum, assignExprVarName, codeSnippet, args);
                        fs.setBody(bs);
                    }
                } else if (methodBody.getStatement(j).isBlockStmt()) {
                    if (methodBody.getStatement(j).getRange().get().begin.line <= assignExprLineNum.peek()) { // Statement inside the loop declaration
                    	if (assignExprLineNum.peek() > Integer.parseInt(args[0]) && assignExprLineNum.peek() < Integer.parseInt(args[1]) && (Arrays.asList(args).contains(assignExprVarName.peek()) || args[2].equals(""))) // args[2].equals("") would mean that the "Variables to Print" section is empty.
                    		methodBody.getStatements().add(++j, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.peek() + ")")));
                        assignExprLineNum.poll();
                        assignExprVarName.poll();
//                        methodBody.getStatements().add(++j, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.poll() + ")")));
//                        assignExprLineNum.poll();
                    } else
                        if (methodBody.getStatement(j).getClass().getName().equals("com.github.javaparser.ast.stmt.BlockStmt")) {
                        parseStatements(targetMethod, (BlockStmt) methodBody.getStatement(j), assignExprLineNum, assignExprVarName, codeSnippet, args);
                    }
                } else if (methodBody.getStatement(j).isIfStmt()) {
                    j = ifStmt(j, assignExprLineNum, assignExprVarName, targetMethod, codeSnippet, methodBody, methodBody.getStatement(j), args);
                } else if (methodBody.getStatement(j).isLabeledStmt()) {
                    if (methodBody.getStatement(j).getChildNodes().get(1) instanceof ForStmt) {
                        ForStmt fs = (ForStmt) methodBody.getStatement(j).getChildNodes().get(1);
                        BlockStmt bs = (BlockStmt) fs.getBody();
                        parseStatements(targetMethod, bs, assignExprLineNum, assignExprVarName, codeSnippet, args);
                    } else if (methodBody.getStatement(j).getChildNodes().get(1) instanceof WhileStmt) {
                        BlockStmt bs = (BlockStmt) methodBody.getStatement(j).getChildNodes().get(1).getChildNodes().get(1);
                        if (methodBody.getStatement(j).getRange().get().begin.line == assignExprLineNum.peek()) {
                        	if (assignExprLineNum.peek() > Integer.parseInt(args[0]) && assignExprLineNum.peek() < Integer.parseInt(args[1]) && (Arrays.asList(args).contains(assignExprVarName.peek()) || args[2].equals(""))) // args[2].equals("") would mean that the "Variables to Print" section is empty.
                        		methodBody.getStatements().add(0, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.peek() + ")")));
                            assignExprLineNum.poll();
                            assignExprVarName.poll();
//                            bs.getStatements().add(0, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.poll() + ")")));
//                            assignExprLineNum.poll();
                        }
                        parseStatements(targetMethod, bs, assignExprLineNum, assignExprVarName, codeSnippet, args);
                    }
                } else if (methodBody.getStatement(j).isDoStmt()) {
                    DoStmt ds = (DoStmt) methodBody.getStatement(j);
                    parseStatements(targetMethod, (BlockStmt) ds.getBody(), assignExprLineNum, assignExprVarName, codeSnippet, args);
                } else if (methodBody.getStatement(j).isForEachStmt()) {
                    ForEachStmt fes = (ForEachStmt) methodBody.getStatement(j);
                    if (fes.getBody().isIfStmt()) {
                        j = ifStmt(j, assignExprLineNum, assignExprVarName, targetMethod, codeSnippet, methodBody, fes.getBody(), args);
                    } else {
                        parseStatements(targetMethod, (BlockStmt) fes.getBody(), assignExprLineNum, assignExprVarName, codeSnippet, args);
                    }
                } else if (methodBody.getStatement(j).isSwitchStmt()) {
                    SwitchStmt ss = (SwitchStmt) methodBody.getStatement(j);
                    for (int k = 1; k < ss.getChildNodes().size(); k++) {
                        SwitchEntry se = (SwitchEntry) ss.getChildNodes().get(k);
                        BlockStmt bs = new BlockStmt(se.getStatements());
                        parseStatements(targetMethod, bs, assignExprLineNum, assignExprVarName, codeSnippet, args);
                    }
                } else { // Expression, Assert
//                	boolean test = assignExprLineNum.peek() > Integer.parseInt(args[0]) && assignExprLineNum.peek() < Integer.parseInt(args[1]) && Arrays.asList(args).contains(assignExprVarName.poll());
                	if (assignExprLineNum.peek() > Integer.parseInt(args[0]) && assignExprLineNum.peek() < Integer.parseInt(args[1]) && (Arrays.asList(args).contains(assignExprVarName.peek()) || args[2].equals(""))) // args[2].equals("") would mean that the "Variables to Print" section is empty.
                		methodBody.getStatements().add(++j, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.peek() + ")")));
                    assignExprLineNum.poll();
                    assignExprVarName.poll();
                }
            }
        }
    }

    public static int ifStmt(int j, Queue<Integer> assignExprLineNum, Queue<String> assignExprVarName, CallableDeclaration targetMethod, Statement codeSnippet, BlockStmt methodBody, Statement s, String[] args) {
        IfStmt is = (IfStmt) s;
        if (is.getThenStmt().getClass().getName().equals("com.github.javaparser.ast.stmt.ExpressionStmt")) { // Handles the case of one line if statement
            if (assignExprLineNum.peek() <= is.getThenStmt().getRange().get().begin.line) { // Checks whether the value being printed is in the if statement of not (The value might be in the else statement)
            	if (assignExprLineNum.peek() > Integer.parseInt(args[0]) && assignExprLineNum.peek() < Integer.parseInt(args[1]) && (Arrays.asList(args).contains(assignExprVarName.peek()) || args[2].equals(""))) // args[2].equals("") would mean that the "Variables to Print" section is empty.
            		methodBody.getStatements().add(++j, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.peek() + ")")));
                assignExprLineNum.poll();
                assignExprVarName.poll();
//                methodBody.getStatements().add(++j, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.poll() + ")")));
//                assignExprLineNum.poll();
            }
        } else {
            parseStatements(targetMethod, (BlockStmt) is.getThenStmt(), assignExprLineNum, assignExprVarName, codeSnippet, args);
        }

        if (is.hasElseBranch()) {
            s = is.getElseStmt().get();
            if (s.isIfStmt()) {
                // Recursion of if.
                j = ifStmt(j, assignExprLineNum, assignExprVarName, targetMethod, codeSnippet, methodBody, s, args);
            } else {
                if (is.getElseStmt().get().isExpressionStmt()) { // Handles the case of one line else statement
                    if (assignExprLineNum.peek() <= is.getElseStmt().get().getRange().get().begin.line) {
                    	if (assignExprLineNum.peek() > Integer.parseInt(args[0]) && assignExprLineNum.peek() < Integer.parseInt(args[1]) && (Arrays.asList(args).contains(assignExprVarName.peek()) || args[2].equals(""))) // args[2].equals("") would mean that the "Variables to Print" section is empty.
                    		methodBody.getStatements().add(++j, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.peek() + ")")));
                        assignExprLineNum.poll();
                        assignExprVarName.poll();
//                        methodBody.getStatements().add(++j, new ExpressionStmt(new NameExpr("System.out.println(" + "\"" + assignExprVarName.peek() + ": \" + " + assignExprVarName.poll() + ")")));
//                        assignExprLineNum.poll();
                    }
                } else {
                    parseStatements(targetMethod, (BlockStmt) is.getElseStmt().get(), assignExprLineNum, assignExprVarName, codeSnippet, args);
                }
            }
        }
        return j;
    }

    public static List<CallableDeclaration> cdSort(List<CallableDeclaration> cd) {
        for (int j = 1; j < cd.size(); j++) {
            CallableDeclaration current = cd.get(j);
            int i = j-1;
                while ((i > -1) && (cd.get(i).getRange().get().begin.line > current.getRange().get().begin.line)) {
                cd.set(i+1, cd.get(i));
                i--;
            }
            cd.set(i+1, current);
        }
        return cd;
    }


}
