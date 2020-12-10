<%--@elvariable id="barcodeDto" type="com.gmail.maxsvynarchuk.presentation.dto.BarcodeDto"--%>
<%--@elvariable id="productDto" type="com.gmail.maxsvynarchuk.presentation.dto.ProductDto"--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
   <head>
      <link rel="stylesheet" href="
      <c:url value="/webjars/bootstrap/4.3.1/css/bootstrap.min.css"/>
      ">
      <link rel="stylesheet" href="
      <c:url value="/resources/assets/bootswatch4/darkly-bootstrap.min.css"/>
      ">
      <link rel="stylesheet" href="
      <c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/>
      ">
      <!--<link rel="stylesheet" href="<c:url value="/resources/css/default.css"/>">-->
      <title>Barcode</title>
   </head>
   <body class="d-flex flex-column min-vh-100">
      <div class="container">
         <hr class="my-4">
         <div class="row">
            <div class="${not empty barcodeDto ? 'col-md text-center' : 'offset-md-3 col-md-6 text-center'}">
               <h2>Generate barcode</h2>
               <div class="container">
                  <form accept-charset="UTF-8" role="form" method="post" action="<c:url value="/add"/>">
                     <div class="form-group ">
                        <label for="exampleFormControlInput1">Product name</label>
                        <input type="text" class="form-control" id="productName" name="productName" placeholder="Product name..." required>
                     </div>
                     <div class="form-group">
                        <label for="exampleFormControlTextarea1">Product description</label>
                        <textarea class="form-control" id="exampleFormControlTextarea1" name="productDescription" rows="3" required></textarea>
                     </div>
                     <div class="form-group">
                        <label for="exampleFormControlSelect1">Country</label>
                        <select class="form-control" id="country" name="countryCode">
                           <option selected value="482">Ukraine</option>
                           <option selected value="590">Poland</option>
                           <option selected value="45">Japan</option>
                           <option selected value="690">China</option>
                           <option selected value="400">Germany</option>
                        </select>
                     </div>
                     <div class="form-group ">
                        <label for="exampleFormControlInput1">Manufacturer name</label>
                        <input type="text" class="form-control" id="manufacturerName" name="manufacturerName" placeholder="Manufacturer name..." required>
                     </div>
                     <div class="form-group ">
                        <label for="exampleFormControlInput1">Manufacturer address</label>
                        <input type="text" class="form-control" id="manufacturerAddress" name="manufacturerAddress" placeholder="Manufacturer address..." required>
                     </div>
                     <button class="btn btn-primary btn-lg btn-success" type="submit">Submit</button>
                  </form>
               </div>
            </div>
            <c:if test="${not empty barcodeDto}">
               <div class="col-md">
                  <div class="row  text-center">
                     <div class="col-12">
                        <img src="<c:url value="/resources/barcodes/${barcodeDto.imageName}"/>" alt="Logo"/>
                     </div>
                     <div class="col-12">
                        <form accept-charset="UTF-8" role="form" method="get" action="<c:url value="/download"/>">
                           <input type="hidden" name="filename" id="filename" value="${barcodeDto.imageName}"/>
                           <div class="form-group">
                              <label for="exampleFormControlInput1">Barcode number</label>
                              <input type="text" class="form-control" id="barcodeNumber" disabled value="${barcodeDto.code}">
                           </div>
                           <button class="btn btn-primary btn-lg btn-success" type="submit">Download</button>
                        </form>
                     </div>
                  </div>
               </div>
            </c:if>
         </div>
         <hr class="my-4">
         <div class="row">
            <c:if test="${invalidBarcodeImage}">
                <div class="offset-md-3 col-md-6 text-center">
                   <div class="alert alert-danger" role="alert">
                     Invalid barcode image!
                   </div>
                </div>
            </c:if>
            <c:if test="${notFoundProduct}">
                <div class="offset-md-3 col-md-6 text-center">
                   <div class="alert alert-warning" role="alert">
                     Product with such code does not exist!
                   </div>
                </div>
            </c:if>
            <div class="${not empty productDto ? 'col-md text-center' : 'offset-md-3 col-md-6 text-center'}">
               <form accept-charset="UTF-8" role="form" method="post" action="<c:url value="/decode"/>" enctype="multipart/form-data">
                  <div class="form-group">
                     <label for="exampleFormControlFile1"><h2>Decode barcode</h2></label>
                     <input type="file" class="form-control-file" id="exampleFormControlFile1" name="barcode" required>
                  </div>
                  <button class="btn btn-primary btn-lg btn-success" type="submit">Upload barcode</button>
               </form>
            </div>
            <c:if test="${not empty productDto}">
               <div class="col-md text-center">
                  <div class="container">
                     <form>
                        <div class="form-group ">
                           <label for="exampleFormControlInput1">Barcode number</label>
                           <input type="text" class="form-control" id="productName" disabled value="${productDto.barcode}">
                        </div>
                        <div class="form-group ">
                           <label for="exampleFormControlInput1">Product name</label>
                           <input type="text" class="form-control" id="productName" disabled value="${productDto.productName}">
                        </div>
                        <div class="form-group">
                           <label for="exampleFormControlTextarea1">Product description</label>
                           <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"  disabled>${productDto.productDescription}</textarea>
                        </div>
                        <div class="form-row">
                           <div class="form-group col-md-6">
                              <label for="inputEmail4">Country code</label>
                              <input type="email" class="form-control" id="inputEmail4" disabled value="${productDto.countryCode}">
                           </div>
                           <div class="form-group col-md-6">
                              <label for="inputPassword4">Country name</label>
                              <input type="text" class="form-control" id="inputPassword4" disabled value="${productDto.countryName}">
                           </div>
                        </div>
                        <div class="form-group ">
                           <label for="exampleFormControlInput1">Manufacturer name</label>
                           <input type="text" class="form-control" id="manufacturerName" disabled value="${productDto.manufacturerName}">
                        </div>
                        <div class="form-group ">
                           <label for="exampleFormControlInput1">Manufacturer address</label>
                           <input type="text" class="form-control" id="manufacturerAddress" disabled value="${productDto.manufacturerAddress}">
                        </div>
                     </form>
                  </div>
               </div>
            </c:if>
         </div>
         <hr class="my-4">
      </div>
   </body>
</html>