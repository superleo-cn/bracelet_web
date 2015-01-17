package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.Ebean;

@Entity
@Table(name = "tb_health_data")
public class Bracelet {

	@Id
	public int id;

	public String motionState;

	public int pulseState;

	public float temperature;

	public String braceletId;

	public static List<Bracelet> search() {
		return Ebean.find(Bracelet.class).findList();
	}

}
