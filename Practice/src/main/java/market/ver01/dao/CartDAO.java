package market.ver01.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import market.ver01.dto.Product;
import market.ver01.dto.CartDTO;
import mvc.database.DBConnection;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

public class CartDAO {
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private Statement statement = null;
	
	public CartDAO() {
		connect();
	}

	private void connect() {
		try {
			connection = DBConnection.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean updateCart(Product productDTO, String orderNo, String memberId) {
		// ������ �ֹ���ȣ�� ���� productId�� ������ update ������ insert
		int flag = 0;
		String productId = productDTO.getProductId();
		System.out.println(orderNo + " " + memberId);
		String sql = "SELECT p_cartId FROM cart WHERE p_orderNo = ? AND p_productId = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, orderNo);
			preparedStatement.setString(2, productId);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				int cartId = resultSet.getInt("p_cartId");
				sql = "UPDATE cart SET p_cnt = p_cnt + 1 WHERE p_cartId = ? ";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, cartId);
				flag = preparedStatement.executeUpdate();
			} else {
				sql = "INSERT INTO cart VALUES(null, ?, ?, ?, ?, ?, ?,  now())";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, memberId);
				preparedStatement.setString(2, orderNo);
				preparedStatement.setString(3, productId);
				preparedStatement.setString(4, productDTO.getPname());
				preparedStatement.setInt(5, productDTO.getUnitPrice());
				preparedStatement.setInt(6, 1);
				flag = preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag == 1;
	}


	public ArrayList<CartDTO> getCartList(String orderNo) {
		ArrayList<CartDTO> cartArrayList = new ArrayList<>();
		String sql = "SELECT * FROM cart WHERE p_orderNo = ?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, orderNo);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				CartDTO cart = new CartDTO();
				cart.setP_cartId(resultSet.getInt("p_cartId"));
				cart.setP_productId(resultSet.getString("p_productId"));
				cart.setP_name(resultSet.getString("p_name"));
				cart.setP_unitPrice(resultSet.getInt("p_unitPrice"));
				cart.setP_cnt(resultSet.getInt("p_cnt"));
				cartArrayList.add(cart);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cartArrayList;
	}

	public boolean updateCartBylogin (HttpSession session) {
		int flag = 0;
		String orderNo = session.getId();
		String id = (String) session.getAttribute("sessionId");
		// ���� �α��ο� ���� ��ǰ ������Ʈ
		String sql = "UPDATE cart SET p_orderNo= ? WHERE p_memberId = ?";
		try {
		
		preparedStatement =connection.prepareStatement(sql);
		preparedStatement.setString(1, orderNo);
		preparedStatement.setString(2, id);
		flag= preparedStatement.executeUpdate();
		// �α��� ���� ���� ��ǰ ������Ʈ
		sql = "UPDATE cart SET p_memberId=? WHERE p_orderNo= ?";
		preparedStatement = connection.prepareStatement(sql);
		
		preparedStatement.setString(1,id);
		preparedStatement.setString(2, orderNo);
		flag =preparedStatement.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag != 0;
	}
	
	public boolean deleteCartById(String orderNo, int cartId) {
		//��ٱ��� ���� ����
		int flag = 0;
		String sql = "SELECT * from cart WHERE p_orderNo=? and p_cartId=?";
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, orderNo);
			preparedStatement.setInt(2, cartId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				sql = "DELETE from cart WHERE p_cartId = ?";
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, cartId);
				flag = preparedStatement.executeUpdate();
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag == 1;
	}

	public boolean deleteCartAll(String orderNo) throws SQLException {
		//��ٱ��� ��ü ����
		int flag = 0;
		String sql ="DELETE FROM cart WHERE p_orderNo = ? " ;
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, orderNo);
		flag =preparedStatement.executeUpdate();
		return flag !=0;
	}
	
	public boolean deleteCartBySelId(String orderNo, String chkdId) throws SQLException{
		//������ ��ǰ�� ����
		int flag = 0;
		String sql = "DELETE FROM cart WHERE orderNo= '" + orderNo + "' AND cartId IN (" + chkdId + ")";
		statement = connection.createStatement();
		flag= statement.executeUpdate(sql);
		return flag !=0;
	}
	
}