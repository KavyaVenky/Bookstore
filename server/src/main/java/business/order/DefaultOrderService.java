package business.order;

import api.ApiException;
import business.BookstoreDbException;
import business.JdbcUtils;
import business.book.Book;
import business.book.BookDao;
import business.book.BookForm;
import business.cart.ShoppingCart;
import business.cart.ShoppingCartItem;
import business.customer.Customer;
import business.customer.CustomerDao;
import business.customer.CustomerForm;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DefaultOrderService implements OrderService {

	private BookDao bookDao;
	private OrderDao orderDao;
	private CustomerDao customerDao;
	private LineItemDao lineItemDao;

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void setLineItemDao(LineItemDao lineItemDao) {
		this.lineItemDao = lineItemDao;
	}

	@Override
	public OrderDetails getOrderDetails(long orderId) {
		// NOTE: THIS METHOD PROVIDED NEXT PROJECT
		Order order = orderDao.findByOrderId(orderId);
		Customer customer = customerDao.findByCustomerId(order.getCustomerId());
		List<LineItem> lineItems = lineItemDao.findByOrderId(orderId);
		List<Book> books = lineItems
				.stream()
				.map(lineItem -> bookDao.findByBookId(lineItem.getBookId()))
				.collect(Collectors.toList());
		return new OrderDetails(order, customer, lineItems, books);
	}

	@Override
    public long placeOrder(CustomerForm customerForm, ShoppingCart cart) {

		validateCustomer(customerForm);
		validateCart(cart);

		// NOTE: MORE CODE PROVIDED NEXT PROJECT
		try (Connection connection = JdbcUtils.getConnection()) {
			Date date = getDate(
					customerForm.getCcExpiryMonth(),
					customerForm.getCcExpiryYear());
			return performPlaceOrderTransaction(
					customerForm.getName(),
					customerForm.getAddress(),
					customerForm.getPhone(),
					customerForm.getEmail(),
					customerForm.getCcNumber(),
					date, cart, connection);
		} catch (SQLException e) {
			throw new BookstoreDbException("Error during close connection for customer order", e);
		}
	}


	private Date getDate(String monthString, String yearString) {
		LocalDate localDate = LocalDate.of(Integer.parseInt(yearString), Integer.parseInt(monthString), 2);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}


	private long performPlaceOrderTransaction(
			String name, String address, String phone,
			String email, String ccNumber, Date date,
			ShoppingCart cart, Connection connection) {
		try {
			connection.setAutoCommit(false);
			long customerId = customerDao.create(
					connection, name, address, phone, email,
					ccNumber, date);
			long customerOrderId = orderDao.create(
					connection,
					cart.getComputedSubtotal() + cart.getSurcharge(),
					generateConfirmationNumber(), customerId);
			for (ShoppingCartItem item : cart.getItems()) {
				lineItemDao.create(connection, customerOrderId,
						item.getBookId(), item.getQuantity());
			}
			connection.commit();
			return customerOrderId;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new BookstoreDbException("Failed to roll back transaction", e1);
			}
			return 0;
		}
	}

	private int generateConfirmationNumber() {
		Random random = new Random();
		return random.nextInt(999999999);
	}


	private void validateCustomer(CustomerForm customerForm) {

    	String name = customerForm.getName();

		if (name == null || name.equals("") || name.length() > 45 || name.length() < 4) {
			throw new ApiException.InvalidParameter("Invalid name field");
		}

		String address = customerForm.getAddress();

		if(address == null || address.equals("") || address.length() > 45 || address.length() < 4){
			throw new ApiException.InvalidParameter("Invalid address field");
		}

		String phone = customerForm.getPhone();

		if(phone == null || phone == ""){
			throw new ApiException.InvalidParameter("Invalid Phone number");
		}

		String phone_cleaned = phone.replace("-","").replace("(", "").replace(")", "").trim();

		String pattern =   "^((\\+1|1)?( |-)?)?(([2-9][0-9]{2})|[2-9][0-9]{2})( |-)?([2-9][0-9]{2}( |-)?[0-9]{4})$";

		Pattern pat = Pattern.compile(pattern);
		Matcher mat = pat.matcher(phone_cleaned);
		if(!mat.matches()){
			throw new ApiException.InvalidParameter("Invalid Phone number");
		}

		String email = customerForm.getEmail();

		if(email == null || email == ""){
			throw new ApiException.InvalidParameter("Invalid email address");
		}

		String email_pattern = "^.*@.+[.].+$";

		Pattern email_pat = Pattern.compile(email_pattern);
		Matcher email_mat = email_pat.matcher(email);
		if(!email_mat.matches()){
			throw new ApiException.InvalidParameter("Invalid email address");
		}

		String ccNumber = customerForm.getCcNumber();
		if(ccNumber == null || ccNumber == ""){
			throw new ApiException.InvalidParameter("Invalid credit card number");
		}
		String ccNumber_cleaned = ccNumber.replace("- ","");

		if(ccNumber_cleaned.length() < 14 || ccNumber_cleaned.length() >16){
			throw new ApiException.InvalidParameter("Invalid credit card number");
		}

		String ccNumber_pattern = "^(?:4[0-9]{12}(?:[0-9]{3,6})?|5[1-5][0-9]{14}|(222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}|6(?:011|5[0-9][0-9])[0-9]{12,15}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35{3}){11}|6[27][0-9]{14}|^(81[0-9]{14,17}))$";

		Pattern ccNumber_pat = Pattern.compile(ccNumber_pattern);
		Matcher ccNumber_mat = ccNumber_pat.matcher(ccNumber_cleaned);

		if(!ccNumber_mat.matches()){
			throw new ApiException.InvalidParameter("Invalid credit card number");
		}

		// TODO: Validation checks for address, phone, email, ccNumber

		if (expiryDateIsInvalid(customerForm.getCcExpiryMonth(), customerForm.getCcExpiryYear())) {
			throw new ApiException.InvalidParameter("Invalid expiry date");

		}
	}

	private boolean expiryDateIsInvalid(String ccExpiryMonth, String ccExpiryYear) {

		// TODO: return true when the provided month/year is before the current month/yeaR
		// HINT: Use Integer.parseInt and the YearMonth class

		YearMonth yearMonth = YearMonth.now();
		int month = Integer.parseInt(ccExpiryMonth);
		int year = Integer.parseInt(ccExpiryYear);

		YearMonth ccYearMonth = YearMonth.of(year, Month.of(month));
		if(yearMonth.isAfter(ccYearMonth)){
			return true;
		}
		return false;

	}

	private void validateCart(ShoppingCart cart) {

		if (cart.getItems().size() <= 0) {
			throw new ApiException.InvalidParameter("Cart is empty.");
		}

		cart.getItems().forEach(item-> {
			if (item.getQuantity() < 0 || item.getQuantity() > 99) {
				throw new ApiException.InvalidParameter("Invalid quantity");
			}
			Book databaseBook = bookDao.findByBookId(item.getBookId());
			// TODO: complete the required validations
			BookForm bf = item.getBookForm();
			if(databaseBook.getPrice() != bf.getPrice()){
				throw new ApiException.InvalidParameter("Invalid price");
			}
			if(databaseBook.getCategoryId() != bf.getCategoryId()){
				throw new ApiException.InvalidParameter("Invalid category");
			}

		});
	}

}
