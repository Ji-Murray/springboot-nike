package jzh.Service;

import jzh.entity.Admin;
import jzh.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    
    @Autowired
    private AdminMapper adminMapper;
    
    public Admin login(String username, String password) {
        Admin admin = adminMapper.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        return null;
    }
    
    public Admin getAdminById(Long id) {
        return adminMapper.findById(id);
    }
    
    public boolean addAdmin(Admin admin) {
        return adminMapper.insert(admin) > 0;
    }
    
    public boolean updateAdmin(Admin admin) {
        return adminMapper.update(admin) > 0;
    }
    
    public boolean deleteAdmin(Long id) {
        return adminMapper.deleteById(id) > 0;
    }
} 