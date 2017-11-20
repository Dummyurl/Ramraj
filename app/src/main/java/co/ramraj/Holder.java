package co.ramraj;

import android.graphics.Bitmap;

public class Holder {
	String Id,Name,Image,Regular_price,Sale_price,Price,Subcatid,Subcount;
	Bitmap bitmap,OD_bitmap;
	String Product_id,Product_name,Product_price,Product_quantity,Product_image;
	Double Product_total_price;
	String Order_id,Order_price,Order_date,Order_status,ORDER_statusdate;

	public String getORDER_statusdate() {
		return ORDER_statusdate;
	}

	public void setORDER_statusdate(String ORDER_statusdate) {
		this.ORDER_statusdate = ORDER_statusdate;
	}

	//	String OD_itemid,OD_Pname,OD_quantity,OD_price,OD_totalprice,OD_imageurl;
   String O_orderon,O_items,O_total,O_email,O_orderid,O_name,O_status,O_deliveredon,O_quantity,O_price,O_address,O_state,O_paymenttype,OD_imageurl;

	String ShopId, ShopName, ShopPhone, ShopEmail, ShopCity, ShopState, ShopCountry, ShopAddress, ShopZipcode, ShopDistance, ShopLat, ShopLong, ShopPhoto;



	public String getO_orderon() {
		return O_orderon;
	}

	public void setO_orderon(String o_orderon) {
		O_orderon = o_orderon;
	}

	public String getO_items() {
		return O_items;
	}

	public void setO_items(String o_items) {
		O_items = o_items;
	}

	public String getO_total() {
		return O_total;
	}

	public void setO_total(String o_total) {
		O_total = o_total;
	}

	public String getO_email() {
		return O_email;
	}

	public void setO_email(String o_email) {
		O_email = o_email;
	}

	public String getO_orderid() {
		return O_orderid;
	}

	public void setO_orderid(String o_orderid) {
		O_orderid = o_orderid;
	}

	public String getO_name() {
		return O_name;
	}

	public void setO_name(String o_name) {
		O_name = o_name;
	}

	public String getO_status() {
		return O_status;
	}

	public void setO_status(String o_status) {
		O_status = o_status;
	}

	public String getO_deliveredon() {
		return O_deliveredon;
	}

	public void setO_deliveredon(String o_deliveredon) {
		O_deliveredon = o_deliveredon;
	}

	public String getO_quantity() {
		return O_quantity;
	}

	public void setO_quantity(String o_quantity) {
		O_quantity = o_quantity;
	}

	public String getO_price() {
		return O_price;
	}

	public void setO_price(String o_price) {
		O_price = o_price;
	}

	public String getO_address() {
		return O_address;
	}

	public void setO_address(String o_address) {
		O_address = o_address;
	}

	public String getO_state() {
		return O_state;
	}

	public void setO_state(String o_state) {
		O_state = o_state;
	}

	public String getO_paymenttype() {
		return O_paymenttype;
	}

	public void setO_paymenttype(String o_paymenttype) {
		O_paymenttype = o_paymenttype;
	}

	String order_product_name;

	public String getOrder_product_name() {
		return order_product_name;
	}

	public void setOrder_product_name(String order_product_name) {
		this.order_product_name = order_product_name;
	}

	public String getOD_imageurl() {
		return OD_imageurl;
	}

	public void setOD_imageurl(String oD_imageurl) {
		OD_imageurl = oD_imageurl;
	}

	public Bitmap getOD_bitmap() {
		return OD_bitmap;
	}

	public void setOD_bitmap(Bitmap oD_bitmap) {
		OD_bitmap = oD_bitmap;
	}
//	public String getOD_itemid() {
//		return OD_itemid;
//	}
//
//	public void setOD_itemid(String oD_itemid) {
//		OD_itemid = oD_itemid;
//	}
//
//
//
//	public String getOD_Pname() {
//		return OD_Pname;
//	}
//
//	public void setOD_Pname(String oD_Pname) {
//		OD_Pname = oD_Pname;
//	}
//
//	public String getOD_quantity() {
//		return OD_quantity;
//	}
//
//	public void setOD_quantity(String oD_quantity) {
//		OD_quantity = oD_quantity;
//	}
//
//	public String getOD_price() {
//		return OD_price;
//	}
//
//	public void setOD_price(String oD_price) {
//		OD_price = oD_price;
//	}
//
//	public String getOD_totalprice() {
//		return OD_totalprice;
//	}
//
//	public void setOD_totalprice(String oD_totalprice) {
//		OD_totalprice = oD_totalprice;
//	}

	public String getOrder_id() {
		return Order_id;
	}

	public void setOrder_id(String order_id) {
		Order_id = order_id;
	}

	public String getOrder_price() {
		return Order_price;
	}

	public void setOrder_price(String order_price) {
		Order_price = order_price;
	}

	public String getOrder_date() {
		return Order_date;
	}

	public void setOrder_date(String order_date) {
		Order_date = order_date;
	}

	public String getOrder_status() {
		return Order_status;
	}

	public void setOrder_status(String order_status) {
		Order_status = order_status;
	}

	public Double getProduct_total_price() {
		return Product_total_price;
	}

	public void setProduct_total_price(Double product_total_price) {
		Product_total_price = product_total_price;
	}

	public String getProduct_id() {
		return Product_id;
	}

	public void setProduct_id(String product_id) {
		Product_id = product_id;
	}

	public String getProduct_name() {
		return Product_name;
	}

	public void setProduct_name(String product_name) {
		Product_name = product_name;
	}

	public String getProduct_price() {
		return Product_price;
	}

	public void setProduct_price(String product_price) {
		Product_price = product_price;
	}

	public String getProduct_quantity() {
		return Product_quantity;
	}

	public void setProduct_quantity(String product_quantity) {
		Product_quantity = product_quantity;
	}

	public String getProduct_image() {
		return Product_image;
	}

	public void setProduct_image(String product_image) {
		Product_image = product_image;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public String getSubcatid() {
		return Subcatid;
	}

	public void setSubcatid(String subcatid) {
		Subcatid = subcatid;
	}

	public String getSubcount() {
		return Subcount;
	}

	public void setSubcount(String subcount) {
		Subcount = subcount;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getRegular_price() {
		return Regular_price;
	}

	public void setRegular_price(String regular_price) {
		Regular_price = regular_price;
	}

	public String getSale_price() {
		return Sale_price;
	}

	public void setSale_price(String sale_price) {
		Sale_price = sale_price;
	}

	public String getPrice() {
		return Price;
	}

	public void setPrice(String price) {
		Price = price;
	}

	public String getShopId() {
		return ShopId;
	}

	public void setShopId(String shopId) {
		ShopId = shopId;
	}

	public String getShopName() {
		return ShopName;
	}

	public void setShopName(String shopName) {
		ShopName = shopName;
	}

	public String getShopPhone() {
		return ShopPhone;
	}

	public void setShopPhone(String shopPhone) {
		ShopPhone = shopPhone;
	}

	public String getShopEmail() {
		return ShopEmail;
	}

	public void setShopEmail(String shopEmail) {
		ShopEmail = shopEmail;
	}

	public String getShopCity() {
		return ShopCity;
	}

	public void setShopCity(String shopCity) {
		ShopCity = shopCity;
	}

	public String getShopState() {
		return ShopState;
	}

	public void setShopState(String shopState) {
		ShopState = shopState;
	}

	public String getShopCountry() {
		return ShopCountry;
	}

	public void setShopCountry(String shopCountry) {
		ShopCountry = shopCountry;
	}

	public String getShopAddress() {
		return ShopAddress;
	}

	public void setShopAddress(String shopAddress) {
		ShopAddress = shopAddress;
	}

	public String getShopZipcode() {
		return ShopZipcode;
	}

	public void setShopZipcode(String shopZipcode) {
		ShopZipcode = shopZipcode;
	}

	public String getShopDistance() {
		return ShopDistance;
	}

	public void setShopDistance(String shopDistance) {
		ShopDistance = shopDistance;
	}

	public String getShopLat() {
		return ShopLat;
	}

	public void setShopLat(String shopLat) {
		ShopLat = shopLat;
	}

	public String getShopLong() {
		return ShopLong;
	}

	public void setShopLong(String shopLong) {
		ShopLong = shopLong;
	}

	public String getShopPhoto() {
		return ShopPhoto;
	}

	public void setShopPhoto(String shopPhoto) {
		ShopPhoto = shopPhoto;
	}
}
