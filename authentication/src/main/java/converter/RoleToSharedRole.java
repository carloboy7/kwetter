package converter;

import shared.restModels.Role;

public class RoleToSharedRole {
    public static Role toSharedRole(models.Role modelRole){
        Role role = new Role();
        role.setName(modelRole.getName());
        return role;
    }
}
