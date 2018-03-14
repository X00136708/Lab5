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
        
       
        Product prod = Product.find.byId(id);
        
       
        Customer customer = (Customer)User.getLoggedIn(session().get("email"));
        
        
        if (customer.getBasket() == null) {
            
            customer.setBasket(new Basket());
            customer.getBasket().setCustomer(customer);
            customer.update();
        }
        
        
        
        
        if(prod.getStock() > 0){
            
            customer.getBasket().addProduct(prod);
            prod.update();
            customer.update();
        } else {
            flash("Product: " + prod.getName() + " is out of stock...");
        }

             
        return ok(basket.render(customer));
    }
    
   
    @Transactional
    public Result addOne(Long id) {
        
        
        OrderItem i = OrderItem.find.byId(id);
      

        if(i.getProduct().getStock() > 0){
            i.increaseQty();
            
        } else {
            flash("Product: " + i.getProduct().getName() + " is out of stock...");
        }

        i.update();
        
        return redirect(routes.ShoppingCtrl.showBasket());
    }

    @Transactional
    public Result removeOne(Long id) {
        
       
        OrderItem i= OrderItem.find.byId(id);   
        Customer c = getCurrentUser(); 
        c.getBasket().removeItem(i);

            

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
        Product prod = new Product();
        Customer customer = (Customer)User.getLoggedIn(session().get("email"));
        
        
        ShopOrder order = new ShopOrder();
        
       
        order.setCustomer(c);
        
        
        order.setItems(c.getBasket().getBasketItems());
        for(OrderItem i: order.getItems()){
            if(prod.getStock() >= 1){
                prod.setStock(prod.getStock()-1);
                
                prod.update();
                customer.update();
            } else {
                flash("Product " + prod.getName() + " is out of stock...");
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