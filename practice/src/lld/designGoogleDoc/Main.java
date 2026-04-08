package lld.designGoogleDoc;

import lld.designGoogleDoc.document.DocumentState;
import lld.designGoogleDoc.model.DeleteOperation;
import lld.designGoogleDoc.model.InsertOperation;
import lld.designGoogleDoc.model.Operation;
import lld.designGoogleDoc.service.DocumentService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        DocumentService service = new DocumentService();
        String docId = "doc1";

        DocumentState doc = service.getOrCreate(docId);

        // Track each user's version
        Map<String, Integer> userVersions = new HashMap<>();

        System.out.println("==== Google Docs Simulation ====");
        System.out.println("Commands:");
        System.out.println("userId insert position text");
        System.out.println("userId delete position length");
        System.out.println("Type 'exit' to quit\n");

        while (true) {

            System.out.println("\nCurrent Document: " + doc.getDocument());
            System.out.print("Enter command: ");

            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) break;

            try {
                String[] parts = input.split(" ");

                String userId = parts[0];
                String operationType = parts[1];

                if (!userVersions.containsKey(userId)) {
                    userVersions.put(userId, doc.getVersion());
                }
                int version = userVersions.get(userId);

                Operation operation;

                if (operationType.equalsIgnoreCase("insert")) {

                    int position = Integer.parseInt(parts[2]);

                    StringBuilder textBuilder = new StringBuilder();
                    for (int i = 3; i < parts.length; i++) {
                        textBuilder.append(parts[i]).append(" ");
                    }
                    String text = textBuilder.toString().trim();

                    operation = new InsertOperation(position, text, version);

                } else if (operationType.equalsIgnoreCase("delete")) {

                    int position = Integer.parseInt(parts[2]);
                    int length = Integer.parseInt(parts[3]);

                    operation = new DeleteOperation(position, length, version);

                } else {
                    System.out.println("Invalid operation!");
                    continue;
                }

                Operation transformed = service.handleOperation(docId, operation);

                userVersions.put(userId, doc.getVersion());

                System.out.println("Applied: " + transformed);
                System.out.println("Updated Document: " + doc.getDocument());

            } catch (Exception e) {
                System.out.println("Invalid input format. Try again.");
            }
        }

        scanner.close();
        System.out.println("Final Document: " + doc.getDocument());
    }
}