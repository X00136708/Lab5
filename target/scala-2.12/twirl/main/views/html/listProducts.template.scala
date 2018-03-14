
package views.html

import _root_.play.twirl.api.TwirlFeatureImports._
import _root_.play.twirl.api.TwirlHelperImports._
import _root_.play.twirl.api.Html
import _root_.play.twirl.api.JavaScript
import _root_.play.twirl.api.Txt
import _root_.play.twirl.api.Xml
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import java.lang._
import java.util._
import scala.collection.JavaConverters._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import play.data._
import play.core.j.PlayFormsMagicForJava._
/*1.2*/import models.products.Category
/*2.2*/import models.products.Product
/*3.2*/import models.users.User

object listProducts extends _root_.play.twirl.api.BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,_root_.play.twirl.api.Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with _root_.play.twirl.api.Template6[List[Product],List[Category],Long,String,User,play.api.Environment,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*4.2*/(products: List[Product], categories: List[Category], catId: Long, filter: String,user: User,env: play.api.Environment):play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {


Seq[Any](format.raw/*4.121*/("""


"""),_display_(/*7.2*/main("Products",user)/*7.23*/ {_display_(Seq[Any](format.raw/*7.25*/("""

"""),format.raw/*9.1*/("""<p class="lead">Product Catalogue</p>
<br>
<div class="row">
  <div class="col-sm-2">
    <h4>Categories</h4>
    <div class="list-group">
      <a href=""""),_display_(/*15.17*/routes/*15.23*/.ProductCtrl.listProducts(0, filter)),format.raw/*15.59*/("""" class="list-group-item">All Categories</a>
      """),_display_(/*16.8*/for(c <- categories) yield /*16.28*/ {_display_(Seq[Any](format.raw/*16.30*/("""
        """),format.raw/*17.9*/("""<a href=""""),_display_(/*17.19*/routes/*17.25*/.ProductCtrl.listProducts(c.getId, filter)),format.raw/*17.67*/("""" class="list-group-item">"""),_display_(/*17.94*/c/*17.95*/.getName),format.raw/*17.103*/("""
          """),format.raw/*18.11*/("""<span class="badge">"""),_display_(/*18.32*/c/*18.33*/.getProducts.size()),format.raw/*18.52*/("""</span>
        </a>
      """)))}),format.raw/*20.8*/("""
    """),format.raw/*21.5*/("""</div>
  </div>
  <div class="col-sm-10">
      """),_display_(/*24.8*/if(flash.containsKey("success"))/*24.40*/ {_display_(Seq[Any](format.raw/*24.42*/("""
        """),format.raw/*25.9*/("""<div class="alert alert-success">
          """),_display_(/*26.12*/flash/*26.17*/.get("success")),format.raw/*26.32*/("""
        """),format.raw/*27.9*/("""</div>
      """)))}),format.raw/*28.8*/("""

      			"""),format.raw/*30.10*/("""<!-- Search Form -->
			<div id="actions">
        <form action=""""),_display_(/*32.24*/routes/*32.30*/.ProductCtrl.listProducts(catId)),format.raw/*32.62*/("""" method="GET">
         <input type="search" id="searchbox" name="filter" value=""""),_display_(/*33.68*/filter),format.raw/*33.74*/("""" placeholder="Filter by product name...">
         <input type="submit" id="searchsubmit" value="Filter by name" class="btn-md btn-primary">
        </form>
       </div>
    <table class="table table-bordered table-hover table-condensed">   
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Description</th>
          <th>Stock</th>
          <th>Price</th>   
        </tr>
      </thead>

      <tbody>
        """),_display_(/*49.10*/for(p<-products) yield /*49.26*/ {_display_(Seq[Any](format.raw/*49.28*/("""
          """),format.raw/*50.11*/("""<tr>
              """),_display_(/*51.16*/if(env.resource("public/images/productImages/thumbnails/" + p.getId + ".jpg").isDefined)/*51.104*/ {_display_(Seq[Any](format.raw/*51.106*/("""
                """),format.raw/*52.17*/("""<td><img src="/assets/images/productImages/thumbnails/"""),_display_(/*52.72*/(p.getId + ".jpg")),format.raw/*52.90*/(""""/></td>
            """)))}/*53.15*/else/*53.20*/{_display_(Seq[Any](format.raw/*53.21*/("""
                """),format.raw/*54.17*/("""<td><img src="/assets/images/productImages/thumbnails/noImage.jpg"/></td>
            """)))}),format.raw/*55.14*/("""
            """),format.raw/*56.13*/("""<td class="numeric">"""),_display_(/*56.34*/p/*56.35*/.getId),format.raw/*56.41*/("""</td>
            <td><a href=""""),_display_(/*57.27*/routes/*57.33*/.ProductCtrl.productDetails(p.getId)),format.raw/*57.69*/("""">
                """),_display_(/*58.18*/p/*58.19*/.getName),format.raw/*58.27*/("""</td>
               </a>     
            <td>"""),_display_(/*60.18*/p/*60.19*/.getDescription),format.raw/*60.34*/("""</td>
            <td  class="numeric">"""),_display_(/*61.35*/p/*61.36*/.getStock),format.raw/*61.45*/("""</td>
            <td  class="numeric">€ """),_display_(/*62.37*/("%.2f".format(p.getPrice))),format.raw/*62.64*/("""</td>
            <td><a href=""""),_display_(/*63.27*/routes/*63.33*/.ShoppingCtrl.addToBasket(p.getId)),format.raw/*63.67*/(""""  class="btn btn-default btn-xs"><span class="glyphicon glyphicon-shopping-cart"></span></a></td>

          </tr>
        """)))}),format.raw/*66.10*/("""
      """),format.raw/*67.7*/("""</tbody>

    </table>
  </div>
</div>
""")))}))
      }
    }
  }

  def render(products:List[Product],categories:List[Category],catId:Long,filter:String,user:User,env:play.api.Environment): play.twirl.api.HtmlFormat.Appendable = apply(products,categories,catId,filter,user,env)

  def f:((List[Product],List[Category],Long,String,User,play.api.Environment) => play.twirl.api.HtmlFormat.Appendable) = (products,categories,catId,filter,user,env) => apply(products,categories,catId,filter,user,env)

  def ref: this.type = this

}


              /*
                  -- GENERATED --
                  DATE: Wed Mar 14 20:22:35 GMT 2018
                  SOURCE: /home/wdd/lab5/app/views/listProducts.scala.html
                  HASH: af755ff45170cf4fefc9014e87b76cbecc24b485
                  MATRIX: 651->1|690->34|728->66|1124->92|1339->211|1368->215|1397->236|1436->238|1464->240|1646->395|1661->401|1718->437|1796->489|1832->509|1872->511|1908->520|1945->530|1960->536|2023->578|2077->605|2087->606|2117->614|2156->625|2204->646|2214->647|2254->666|2312->694|2344->699|2419->748|2460->780|2500->782|2536->791|2608->836|2622->841|2658->856|2694->865|2738->879|2777->890|2870->956|2885->962|2938->994|3048->1077|3075->1083|3556->1537|3588->1553|3628->1555|3667->1566|3714->1586|3812->1674|3853->1676|3898->1693|3980->1748|4019->1766|4060->1789|4073->1794|4112->1795|4157->1812|4275->1899|4316->1912|4364->1933|4374->1934|4401->1940|4460->1972|4475->1978|4532->2014|4579->2034|4589->2035|4618->2043|4693->2091|4703->2092|4739->2107|4806->2147|4816->2148|4846->2157|4915->2199|4963->2226|5022->2258|5037->2264|5092->2298|5248->2423|5282->2430
                  LINES: 24->1|25->2|26->3|31->4|36->4|39->7|39->7|39->7|41->9|47->15|47->15|47->15|48->16|48->16|48->16|49->17|49->17|49->17|49->17|49->17|49->17|49->17|50->18|50->18|50->18|50->18|52->20|53->21|56->24|56->24|56->24|57->25|58->26|58->26|58->26|59->27|60->28|62->30|64->32|64->32|64->32|65->33|65->33|81->49|81->49|81->49|82->50|83->51|83->51|83->51|84->52|84->52|84->52|85->53|85->53|85->53|86->54|87->55|88->56|88->56|88->56|88->56|89->57|89->57|89->57|90->58|90->58|90->58|92->60|92->60|92->60|93->61|93->61|93->61|94->62|94->62|95->63|95->63|95->63|98->66|99->67
                  -- GENERATED --
              */
          