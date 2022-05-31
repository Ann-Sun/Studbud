package com.example.project.service;

    import java.util.List;

    import org.springframework.beans.BeanUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Example;
    import org.springframework.stereotype.Service;
    
    import com.example.project.model.Bookmark;
    import com.example.project.repository.BookmarkRep;
    @Service
    public class BookmarkService {
        @Autowired
        BookmarkRep bookmarkRep;
    
        //CREATE
        public Bookmark create(Bookmark bookmark) {
            return bookmarkRep.save(bookmark);
        }
    
        //READ
        public List<Bookmark> read() {
            return bookmarkRep.findAll();
        }

        public Bookmark findOne(Long bookmarkId) {
            return bookmarkRep.findById(bookmarkId).get();
        }
    
        //UPDATE
        public Bookmark update(Long bookmarkId, Bookmark bookmarkDetails) {
            Bookmark bookmark = bookmarkRep.findById(bookmarkId).get();
            BeanUtils.copyProperties(bookmarkDetails, bookmark);
            return bookmarkRep.save(bookmark);
        }
    
        //DELETE
        public void delete(Long bookmarkId) {
            bookmarkRep.deleteById(bookmarkId);
        }
    }
    