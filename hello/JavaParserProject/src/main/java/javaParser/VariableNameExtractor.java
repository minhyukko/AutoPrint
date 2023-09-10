package javaParser;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class VariableNameExtractor extends VoidVisitorAdapter<List<String>>  {
	
	public VariableNameExtractor( ) {
		
	}

    public static void main(String[] args) {
        // Replace "YourJavaFile.java" with the path to your Java file
        String filePath = "C:\\Users\\minhy\\Downloads\\Sample Programs-20230815T174349Z-001\\Sample Programs\\BugProgram.java";
        List<String> variableNames = extractVariableNames(filePath);
    }
    
    @Override
    public void visit(VariableDeclarator declarator, List<String> collector) {
        String variableName = declarator.getNameAsString();
        if (!collector.contains(variableName)) {
            collector.add(variableName);
        }
        super.visit(declarator, collector);
    }


    public static List<String> extractVariableNames(String filePath) {
        List<String> variableNames = new ArrayList<>();

        try {
            CompilationUnit cu = StaticJavaParser.parse(new File(filePath));
            VariableNameExtractor variableExtractor = new VariableNameExtractor();
            variableExtractor.visit(cu, variableNames);
//            cu.findAll(FieldDeclaration.class).forEach(fieldDeclaration -> {
//                fieldDeclaration.getVariables().forEach(variableDeclarator -> {
//                    variableNames.add(variableDeclarator.getNameAsString());
//                });
//            });

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

        System.out.println("Variable names in the Java file:");
        for (String variableName : variableNames) {
            System.out.println(variableName);
        }

        return variableNames;
    }
}
