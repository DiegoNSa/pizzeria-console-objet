package fr.pizzeria.console;

import java.util.Scanner;
import fr.pizzeria.model.Pizza;

public class PizzeriaAdminConsoleApp {
	public static Pizza[] increaseSize(Pizza[] oldArray,int newSize) {
		Pizza[] newArray = new Pizza[newSize];
		//copy content of array
		for(int i = 0;i < oldArray.length;i++) {
			newArray[i]=oldArray[i];
		}
		return newArray;
	}
	private static Pizza[] addPizza(int index,Pizza newPizza, Pizza[] pizzaArray) {
		if(index >= pizzaArray.length) {
			pizzaArray = PizzeriaAdminConsoleApp.increaseSize(pizzaArray, (index+1)*2);
		}
		if(pizzaArray[index] == null) {
			pizzaArray[index] = newPizza;
		}
		else {
			System.out.println("Id deja pris.");
		}
		return pizzaArray;
	}
	
	public static void main(String[] args) {
		Scanner questionUser = new Scanner(System.in);
		boolean exit = false;
		
		String codeToFind;
		int indexOfPizzaToModify = 0;
		Pizza[] mutablePizzaArray = new Pizza[0];
		
		while(!exit) {
			System.out.println("*****Pizzeria Administration*****");
			System.out.println("1. Lister les pizzas");
			System.out.println("2. Ajouter une nouvelle pizza");
			System.out.println("3. Mettre à jour une pizza");
			System.out.println("4. Supprimer une pizzas");
			System.out.println("99. Sortir");
			
			int chosenOption = questionUser.nextInt();
			
			switch(chosenOption) {
			case 1:
				System.out.println("Liste des Pizza");
				for(int i=0;i < mutablePizzaArray.length; i++) {
					Pizza currentPizza = mutablePizzaArray[i];
					if(mutablePizzaArray[i] != null) {
						System.out.println("Pizza n°" + currentPizza.id + ": " + currentPizza.code + " -> " + currentPizza.libelle + "(" + currentPizza.prix + "€)");
					}
				}
				break;
			case 2:
				System.out.println("Ajout d'une nouvelle pizza");
				System.out.println("Veuillez saisir le code");
				String newCode = questionUser.next();
				System.out.println("Veuillez saisir le nom (sans espace)");
				String newName = questionUser.next();
				System.out.println("Veuillez saisir le prix");
				double newPrice = questionUser.nextDouble();
				Pizza newPizza = new Pizza(newCode, newName, newPrice);
				mutablePizzaArray = PizzeriaAdminConsoleApp.addPizza(newPizza.id, newPizza, mutablePizzaArray);
				
				break;
			case 3:
				System.out.println("Veuillez choisir le code de la pizza à modifier");
				codeToFind = questionUser.next();
				indexOfPizzaToModify = -1;
				for(int i = 0; i < mutablePizzaArray.length; i++) {
					Pizza currentPizza = mutablePizzaArray[i];
					if(currentPizza != null) {
						if(codeToFind.contentEquals(currentPizza.code)) {
							indexOfPizzaToModify = i;
							break;
						}
					}
				}
				Pizza pizzaToModify = mutablePizzaArray[indexOfPizzaToModify];
				System.out.println("Veuillez saisir le code");
				String modifiedCode = questionUser.next();
				System.out.println("Veuillez saisir le nom (sans espace)");
				String modifiedName = questionUser.next();
				System.out.println("Veuillez saisir le prix");
				
				double modifiedPrice = questionUser.nextDouble();
				pizzaToModify.code = modifiedCode;
				pizzaToModify.libelle = modifiedName;
				pizzaToModify.prix = modifiedPrice;
				

				
				break;
			case 4:
				System.out.println("Veuillez choisir le code de la pizza à supprimer");
				codeToFind = questionUser.next();
				indexOfPizzaToModify = -1;
				for(int i = 0; i < mutablePizzaArray.length; i++) {
					Pizza currentPizza = mutablePizzaArray[i];
					if(currentPizza != null && codeToFind.contentEquals(currentPizza.code)) {
						indexOfPizzaToModify = i;
						break;
					}
				}
				if(indexOfPizzaToModify != -1) {
					mutablePizzaArray[indexOfPizzaToModify] = null;
				}
				break;
			case 99:
				System.out.println("Aurevoir :(");
				break;
			default:
				System.out.println("invalid option.");
			}
		}
		

	}

}
