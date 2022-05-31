package com.example.project.service;

    import java.util.List;
    import java.util.Optional;

    import org.springframework.beans.BeanUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Example;
    import org.springframework.stereotype.Service;
    
    import com.example.project.model.Category;
    import com.example.project.repository.CategoryRep;
    @Service
    public class CategoryService {
        @Autowired
        CategoryRep categoryRep;
    
        //CREATE
        public Category create(Category category) {
            return categoryRep.save(category);
        }
    
        //READ
        public List<Category> read() {
            return categoryRep.findAll();
        }

        public Category findOne(Long categoryId) {
            Optional<Category> category = categoryRep.findById(categoryId);
            return category.get();
        }
    
        //UPDATE
        public Category update(Long categoryId, Category categoryDetails) {
            Category category = categoryRep.findById(categoryId).get();
            BeanUtils.copyProperties(categoryDetails, category);
            return categoryRep.save(category);
        }
    
        //DELETE
        public void delete(Long categoryId) {
            categoryRep.deleteById(categoryId);
        }
    }
    