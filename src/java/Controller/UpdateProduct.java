/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Dal.BrandDao;
import Dal.CategoryDao;
import Dal.ProductDao;
import Model.Brand;
import Model.Category;
import Model.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;

/**
 *
 * @author TRONG TAI
 */
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB
public class UpdateProduct extends HttpServlet {

    private static final String IMAGE_UPLOAD_DIR = "images";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditProductController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        ProductDao dao = new ProductDao();
        Product product = dao.getProductById(productId);
        request.setAttribute("product", product);
        CategoryDao cdao = new CategoryDao();
        List<Category> cl = cdao.getAllCategory();
        request.setAttribute("category", cl);
        BrandDao bdao = new BrandDao();
        List<Brand> list = bdao.getAllBrand();
        request.setAttribute("brand", list);
        request.getRequestDispatcher("updateProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ProductDao pd = new ProductDao();
            int category = Integer.parseInt(request.getParameter("categoryId"));
            int brand = Integer.parseInt(request.getParameter("brandId"));
            String name = request.getParameter("name");
//            String image = request.getParameter("image");
            String description = request.getParameter("description");
            int price = Integer.parseInt(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            int productId = Integer.parseInt(request.getParameter("productId"));
            // Lấy tên file ảnh cũ từ DB
            Product existingProduct = pd.getProductById(productId);
            String currentImage = existingProduct.getImage(); // tên file ảnh cũ

            Part filePart = request.getPart("imageFile");
            String newImageName = null;

            if (filePart != null && filePart.getSize() > 0) {
                // 1. Lấy tên file gốc
                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

                // 2. Tạo tên file mới
                newImageName = System.currentTimeMillis() + "_" + fileName;

                // 3. Đường dẫn thư mục images
                String appPath = request.getServletContext().getRealPath("/" + IMAGE_UPLOAD_DIR);
                File uploadDir = new File(appPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // 4. Lưu file mới
                String filePath = appPath + File.separator + newImageName;
                filePart.write(filePath);

                // 5. Xóa file cũ (nếu có)
                File oldFile = new File(appPath + File.separator + currentImage);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

// 6. Lưu tên ảnh vào DB
// Đảm bảo lưu theo dạng: images/tenfile.jpg
            String imageToSave = (newImageName != null)
                    ? IMAGE_UPLOAD_DIR + "/" + newImageName
                    : currentImage;  // currentImage đã có dạng images/tenfile.jpg

            pd.updateProduct(name, imageToSave, price, description, stock, brand, category, productId);
            response.sendRedirect("manageproduct");
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input data. Please ensure all fields are filled correctly.");
            doGet(request, response); // Redisplay the form with error message
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
