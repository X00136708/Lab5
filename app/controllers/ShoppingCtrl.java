package controllers;

import play.mvc.*;
import play.data.*;
import javax.inject.Inject;

import views.html.*;
import play.db.ebean.Transactional;
import play.api.Environment;


import models.users.*;
import models.products.*;
import models.shopping.*;


import controllers.security.*;


@Security.Authenticated(Secured.class)

@With(CheckIfCustomer.class)

public class ShoppingCtrl extends Controller {



    private FormFactory formFactory;

   
    private Environment env;

    
    @Inject
    public ShoppingCtrl(Environment e, FormFactory f) {
        this.env = e;
        this.formFactory = f;
    }


    
   
	private Customer getCurrentUser() {
		return (Customer)User.getLoggedIn(session().get("email"));
	}

    @Transactional
    public Result showBasket() {
        return ok(basket.render(getCurrentUser()));
    }
    
    
    @Transactional
    public Result addToBasket(Long id) {
        
       
        Product p = Product.find.byId(id);
        
       
        Customer customer = (Customer)User.getLoggedIn(session().get("email"));
        
        
        if (customer.getBasket() == null) {
            
            customer.setBasket(new Basket());
            customer.getBasket().setCustomer(customer);
            customer.update();
        }
        
        
        
        
        if(p.getStock() >= 1){
            //p.setStock(p.getStock()-1);
            customer.getBasket().addProduct(p);
            p.update();
            customer.update();
        } else {
            flash("failure", "Product " + p.getName() + " is out of stock!");
        }

             
        return ok(basket.render(customer));
    }
    
   
    @Transactional
    public Result addOne(Long itemId) {
        
        
        OrderItem item = OrderItem.find.byId(itemId);
      

        if(item.getProduct().getStock() >= 1){
            item.increaseQty();
            
        } else {
            flash("Product: " + item.getProduct().getName() + " is out of stock...");
        }

        item.update();
        
        return redirect(routes.ShoppingCtrl.showBasket());
    }

    @Transactional
    public Result removeOne(Long itemId) {
        
       
        OrderItem item = OrderItem.find.byId(itemId);
        
        Customer c = getCurrentUser();
        
        c.getBasket().removeItem(item);

            

        c.getBasket().update();
        
        return ok(basket.render(c));
    }

    @Transactional
    public Result emptyBasket() {
        
        Customer c = getCurrentUser();
        c.getBasket().removeAllItems();
        c.getBasket().update();
        
        return ok(basket.render(c));
    }

    @Transactional
    public Result placeOrder() {
        Customer c = getCurrentUser();
        Product p = new Product();
        Customer customer = (Customer)User.getLoggedIn(session().get("email"));
        
        
        ShopOrder order = new ShopOrder();
        
       
        order.setCustomer(c);
        
        
        order.setItems(c.getBasket().getBasketItems());
        for(OrderItem i: order.getItems()){
            if(p.getStock() >= 1){
                p.setStock(p.getStock()-1);
                
                p.update();
                customer.update();
            } else {
                flash("Product " + p.getName() + " is out of stock...");
            }
        }
        
        order.save();
       
       
        for (OrderItem i: order.getItems()) {
            
            i.setOrder(order);
            
            i.setBasket(null);
            
            i.update();

           

            

        }
        
     
      
        
        order.update();
        
        
        emptyBasket();
        
        
        return ok(orderConfirmed.render(c, order));
    }
    
   
    @Transactional
    public Result viewOrder(long id) {
        ShopOrder order = ShopOrder.find.byId(id);
        return ok(orderConfirmed.render(getCurrentUser(), order));
    }

}