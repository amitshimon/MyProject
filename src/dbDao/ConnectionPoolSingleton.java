package dbDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ConnectionPoolSingleton {

	private Connection connection;
	private static final int MAX_CONNECTIONS = 10;
	private static ConnectionPoolSingleton instance = null;
	private Collection<Connection> pool;
	// private Collection<Connection> connectionOut;

	// Class private constructor
	private ConnectionPoolSingleton() {

	}

	// Method check if create an instance and if it create send you to init()
	// method if not return new instance
	public static ConnectionPoolSingleton getInstance() {
		if (instance == null) {
			instance = new ConnectionPoolSingleton();
			try {
				instance.init();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	// Method check the number of connection in the pool and if number of
	// connection smaller
	// then 10 it add connection to pool (call to createConnection()method)
	private void init() throws Exception {
		pool = new ArrayList<Connection>();
		for (int i = 0; i < MAX_CONNECTIONS; i++) {
			pool.add(createConnection());
		}
	}

	// method create connection
	public Connection createConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_dao?useSSL=false", "root", "root");
		return connection;
	}

	// Method to management connections
	public synchronized Connection getConnection() {

		try {
			while (pool.size() == 0) {
				wait();

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		connection = pool.iterator().next();
		pool.remove(connection);
		notifyAll();

		return connection;
	}

	// Method to return connection to the pool
	public synchronized void returnConnection(Connection connection) {
		while (pool.size() == 10) {
			try {
				wait();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}
		// connectionOut.remove(connection);
		pool.add(connection);
		notifyAll();
	}

	// Method that close all connections
	public void closeAllConnection() {
		try {
			for (Connection connection: pool) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
