package com.univaq.eaglelibrary.hanlder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.univaq.eaglelibrary.dto.LoginRequestDTO;
import com.univaq.eaglelibrary.dto.ResultDTO;
import com.univaq.eaglelibrary.dto.UserDTO;
import com.univaq.eaglelibrary.persistence.PersistenceService;
import com.univaq.eaglelibrary.persistence.exceptions.DatabaseException;

public class UserHanlder {

	private final Logger logger = LoggerFactory.getLogger(UserHanlder.class);
	private PersistenceService persistenceService;

	public UserHanlder(PersistenceService persistenceService) {
		this.persistenceService = persistenceService;
	}

	public UserDTO login(LoginRequestDTO loginRequestDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultDTO registration(UserDTO userDTO) {
//		ResultSet rsObj = null;
//		Connection connObj = null;
//		PreparedStatement pstmtObj = null;
//		ConnectionPool jdbcObj = new ConnectionPool();
		ResultDTO resultDTO = null;
//		DataSource dataSource=null;
//		try {
//			dataSource = jdbcObj.setUpPool();
//			// Performing Database Operation!
//			System.out.println("\n=====Making A New Connection Object For Db Transaction=====\n");
//			connObj = dataSource.getConnection();
//		
//			pstmtObj = connObj.prepareStatement("SELECT * FROM user");
//			rsObj = pstmtObj.executeQuery();
//			
//			while (rsObj.next()) {
//				System.out.println("Username: " + rsObj.getString("tech_username"));
//			}
//			jdbcObj.printDbStatus();
//			System.out.println("\n=====Releasing Connection Object To Pool=====\n");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String kind = "user";
		String condition = "first_name = 'sab'";
		try {
			persistenceService.search(kind, condition);
		} catch (DatabaseException e) {
			//throw new DatabaseException();
			e.printStackTrace();
		}
		
		
 		resultDTO = new ResultDTO();
		resultDTO.setSuccessfullyOperation(Boolean.TRUE);
		return resultDTO;
	}

	public ResultDTO logout(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
