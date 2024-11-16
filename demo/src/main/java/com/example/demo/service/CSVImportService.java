package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.Website;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.WebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.List;

@Service
public class CSVImportService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WebsiteRepository websiteRepository;

    @Autowired
    private ProductRepository productRepository;

    public void importCategories() {
        try (CSVReader reader = new CSVReader(new FileReader("demo\\src\\main\\files\\category.csv"))) {
            // Skip the first row (header)
            reader.readNext();
            
            List<String[]> rows = reader.readAll();
            rows.forEach(row -> {
                Category category = new Category();
                category.setId(Long.parseLong(row[0]));  // Assuming the ID is in the first column
                category.setName(row[1]);  // Assuming the name is in the second column
                categoryRepository.save(category);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void importWebsites() {
        try (CSVReader reader = new CSVReader(new FileReader("demo\\src\\main\\files\\website.csv"))) {
            // Skip the first row (header)
            reader.readNext();
            
            List<String[]> rows = reader.readAll();
            rows.forEach(row -> {
                Website website = new Website();
                website.setId(Long.parseLong(row[0]));  
                website.setName(row[1]);  
                websiteRepository.save(website);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPath(String website){
        if (website.equals("Newgg")){
            return "demo\\src\\main\\files\\NewggProducts.csv";
        }
        else if(website.equals("Amazon")){
            return "demo\\src\\main\\files\\AmazonProducts.csv";
        }
        else{
            return "";
        }
    }

    public void importProducts(String websiteName) {
        try (CSVReader reader = new CSVReader(new FileReader(getPath(websiteName)))) {
            reader.readNext();

            List<String[]> rows = reader.readAll();
            rows.forEach(row -> {
                Product product = new Product(
                row[2],  // Title
                row[3],  // Description
                Double.parseDouble(row[4]),  // Price
                row[5],  // Link
                Double.parseDouble(row[6]),  // Rating
                row[7],  // Image 1
                row[8],  // Image 2
                row[9],  // Image 3
                row[10]   // Image 4
                );
                // Handle Category (getting Category based on name)
                String categoryName = row[1];  // Category name from column 11 (index 10)
                Category category = categoryRepository.findByName(categoryName);
                product.setCategory(category);

                Website website = websiteRepository.findByName(websiteName);  // Static reference (example)
                product.setWebsite(website);

                // Save product to repository
                productRepository.save(product);
            });
        } catch (Exception e) {
            e.printStackTrace();  // Consider using logging instead of printStackTrace()
        }
    }

}
