package com.shoppingcart.anzdemo.services;

public class ShoppingCartMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if(args == null || args.length == 0){
			System.out.println("Please enter an application name.. registration, customer, inventory or checkout");
		}
		else{
			for(int i=0;i<args.length;i++){
				switch(args[i]){
				case "customer":
					CustomerServer.main(args);
					break;
				case "inventory":
					InventoryServer.main(args);
					break;
				case "checkout":
					CheckoutOrderServer.main(args);
					break;
				case "registration":
					EurekaRegistrationServer.main(args);
					break;
					default:
						System.out.println("Please enter any of 4 application names (registration, customer, inventory or checkout");
				}
			}
		}
	}
}