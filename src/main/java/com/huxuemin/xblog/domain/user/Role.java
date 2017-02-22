package com.huxuemin.xblog.domain.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.huxuemin.xblog.database.mapper.OneToManyColumn;
import com.huxuemin.xblog.database.mapper.PrimaryKeyColumn;
import com.huxuemin.xblog.database.mapper.Table;
import com.huxuemin.xblog.infrastructure.AuthConstant;
import com.huxuemin.xblog.infrastructure.DomainObject;

@Table(name = "ROLES")
public class Role extends DomainObject {

    @PrimaryKeyColumn(columnName = "name")
    private String name;

    // private List<AuthConstant> constantAuth;

    @OneToManyColumn(columnName = "authority", foreignKeyColumnName = "name", foreigntableName = "rolesauth")
    private List<String> strintAuth;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    private List<AuthConstant> constantAuth() {
        List<AuthConstant> constantAuth = new ArrayList<AuthConstant>();
        if (strintAuth != null) {
            Iterator<String> it = strintAuth.iterator();
            while (it.hasNext()) {
                constantAuth.add(AuthConstant.valueOf(it.next()));
            }
        }
        return constantAuth;
    }

    public List<AuthConstant> getAuthList() {
        return constantAuth();
    }

    public String getName() {
        return this.name;
    }

    public void addAuth(AuthConstant auth) {
        if (!hasAuth(auth)) {
            strintAuth.add(auth.toString());
        }
    }

    public void removeAuth(AuthConstant auth) {
        Iterator<String> it = strintAuth.iterator();
        while (it.hasNext()) {
            if (it.next().equals(auth.toString())) {
                it.remove();
                break;
            }
        }
    }

    public boolean hasAuth(AuthConstant auth) {
        boolean result = false;
        Iterator<String> it = strintAuth.iterator();
        while ((!result) && it.hasNext()) {
            result = (it.next().equals(auth.toString()));
        }
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
