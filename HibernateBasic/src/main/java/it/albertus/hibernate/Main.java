package it.albertus.hibernate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

	public static void main(String... args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_test");
		System.out.println("Fine!");
	}
}
