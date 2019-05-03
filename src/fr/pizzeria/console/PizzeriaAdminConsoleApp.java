package fr.pizzeria.console;

import java.util.Scanner;
import fr.pizzeria.model.Pizza;

public class PizzeriaAdminConsoleApp {
	private static Scanner questionUser = new Scanner(System.in);
	
	public static double askForDouble() {
		double newPrice = 0.0;
		boolean success = false;
		while(!success) {
			try {
				newPrice = questionUser.nextDouble();
				success = true;
			}catch(java.util.InputMismatchException ex) {
				System.out.println("format invalide. retapez la valeur ");
				questionUser.next();
			}
		}
		return newPrice;
	}
	
	public static int askForInt() {
		int newPrice = 0;
		boolean success = false;
		while(!success) {
			try {
				newPrice = questionUser.nextInt();
				success = true;
			}catch(java.util.InputMismatchException ex) {
				System.out.println("format invalide (exemple : '10,75'). retapez la valeur ");
				questionUser.next();
			}
		}
		return newPrice;
	}
	
	//check if the code is unique, if not ask again for a code until it is unique
	public static String createNewCode(Pizza[] pizzaArray) {
		return createNewCode(pizzaArray,null);
	}
	
	//check if the code is unique, if not ask again for a code until it is unique
	public static String createNewCode(Pizza[] pizzaArray, String authorizedCode) {
		boolean pizzaIsUnique = false;
		String newCode = "";
		while(!pizzaIsUnique) {
			System.out.println("Veuillez saisir le code");
			 newCode = questionUser.next();

			pizzaIsUnique = true;
			for(int i = 0; i < pizzaArray.length; i++) {
				Pizza currentPizza = pizzaArray[i];
				//check if the pizza exist
				if(currentPizza != null) {
					//check if the code fits
					if(newCode.contentEquals(currentPizza.code) && !(authorizedCode != null && newCode.contentEquals(authorizedCode))) {
						pizzaIsUnique = false;
						break;
					}
				}
			}
			if(!pizzaIsUnique) {
				System.out.println("Attention ! le code " + newCode + " est déja attribué a une autre pizza");
			}
		}
		return newCode;
	}
	
	public static Pizza createPizza(Pizza[] pizzaArray) {
		String newCode = createNewCode(pizzaArray);
		System.out.println("Veuillez saisir le nom (sans espace)");
		String newName = questionUser.next();
		System.out.println("Veuillez saisir le prix");
		double newPrice = askForDouble();
		
		//create the pizza and add it to the list
		Pizza newPizza = new Pizza(newCode, newName, newPrice);
		return newPizza;
	}
	
	//increase the size of the given array with the given new size
	public static Pizza[] increaseSize(Pizza[] oldArray,int newSize) {
		Pizza[] newArray = new Pizza[newSize];
	
		//copy content of array
		for(int i = 0;i < oldArray.length;i++) {
			newArray[i]=oldArray[i]; 
		}
		return newArray;
	}
	
	//add a pizza at the specified index if possible, if not increase the size of the array beforehand
	private static Pizza[] addPizza(int index,Pizza newPizza, Pizza[] pizzaArray) {
		
		//increase the size of the array if too small
		if(index >= pizzaArray.length) {
			pizzaArray = PizzeriaAdminConsoleApp.increaseSize(pizzaArray, (index+1)*2);
		}
		
		//prevent overwriting an existing element
		if(pizzaArray[index] == null) {
			pizzaArray[index] = newPizza;
		}
		else {
			System.out.println("Id deja pris.");
		}
		return pizzaArray;
	}
	
	public static void main(String[] args) {
		boolean exit = false;
		
		String codeToFind;
		int indexOfPizzaToModify = 0;
		
		//initialize pizza array
		Pizza[] mutablePizzaArray = new Pizza[8];
		mutablePizzaArray[0] = new Pizza("PEP","Pépéroni",12.5);
		mutablePizzaArray[1] = new Pizza("MAR","Margherita",14.0);
		mutablePizzaArray[2] = new Pizza("REIN","La Reine",11.5);
		mutablePizzaArray[3] = new Pizza("FRO","La 3 fromage",12.0);
		mutablePizzaArray[4] = new Pizza("CAN","La cannibale",12.5);
		mutablePizzaArray[5] = new Pizza("SAV","La savoyarde",13.0);
		mutablePizzaArray[6] = new Pizza("ORI","L'orientale",13.5);
		mutablePizzaArray[7] = new Pizza("IND","L'indienne",14.0);
		
		while(!exit) {
			System.out.println("*****Pizzeria Administration*****");
			System.out.println("1. Lister les pizzas");
			System.out.println("2. Ajouter une nouvelle pizza");
			System.out.println("3. Mettre à jour une pizza");
			System.out.println("4. Supprimer une pizzas");
			System.out.println("99. Sortir");
			
			int chosenOption = askForInt();
			
			switch(chosenOption) {
			case 1:
				//display the pizza list
				System.out.println("Liste des Pizza");
				for(int i=0;i < mutablePizzaArray.length; i++) {
					Pizza currentPizza = mutablePizzaArray[i];
					if(mutablePizzaArray[i] != null) {
						System.out.println("Pizza n°" + currentPizza.id + ": " + currentPizza.code + " -> " + currentPizza.libelle + "(" + currentPizza.prix + "€)");
					}
				}
				break;
			case 2:
				//add a new pizza
				System.out.println("Ajout d'une nouvelle pizza");
				
								
				//create the pizza and add it to the list
				Pizza newPizza = createPizza(mutablePizzaArray);
				mutablePizzaArray = addPizza(newPizza.id, newPizza, mutablePizzaArray);
				
				break;
			case 3:
				System.out.println("Veuillez choisir le code de la pizza à modifier");
				codeToFind = questionUser.next();
				
				//look for the given code in the list
				indexOfPizzaToModify = -1;
				for(int i = 0; i < mutablePizzaArray.length; i++) {
					Pizza currentPizza = mutablePizzaArray[i];
					//check if the pizza exist
					if(currentPizza != null) {
						//check if the code fits
						if(codeToFind.contentEquals(currentPizza.code)) {
							indexOfPizzaToModify = i;
							break;
						}
					}
				}
				
				if(indexOfPizzaToModify == -1) {
					System.out.println("Le code spécifié n'existe pas.");
				}
				else {
					//ask for the new values
					Pizza pizzaToModify = mutablePizzaArray[indexOfPizzaToModify];
					String modifiedCode = createNewCode(mutablePizzaArray,codeToFind);
					System.out.println("Veuillez saisir le nom (sans espace)");
					String modifiedName = questionUser.next();
					System.out.println("Veuillez saisir le prix");
					double modifiedPrice = askForDouble();
					
					pizzaToModify.code = modifiedCode;
					pizzaToModify.libelle = modifiedName;
					pizzaToModify.prix = modifiedPrice;
				}

				
				break;
			case 4:
				System.out.println("Veuillez choisir le code de la pizza à supprimer");
				//look for the specified code
				codeToFind = questionUser.next();
				indexOfPizzaToModify = -1;
				for(int i = 0; i < mutablePizzaArray.length; i++) {
					Pizza currentPizza = mutablePizzaArray[i];
					if(currentPizza != null && codeToFind.contentEquals(currentPizza.code)) {
						indexOfPizzaToModify = i;
						break;
					}
				}
				
				//remove the pizza if possible
				if(indexOfPizzaToModify != -1) {
					mutablePizzaArray[indexOfPizzaToModify] = null;
				}
				else {
					System.out.println("Le code spécifié n'existe pas.");
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
