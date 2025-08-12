package nirmalya.aathithya.webmodule.recruitment.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReviewHiringRatingModel {
	
	private String ratingCat;
	private String ratingCatName;
	private String ratingType;
	private String ratingTypeName;
	private String rating;
	private String comment;
	private String skillId;
	private String skillName;
	
	public ReviewHiringRatingModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public String getRatingCat() {
		return ratingCat;
	}


	public void setRatingCat(String ratingCat) {
		this.ratingCat = ratingCat;
	}


	public String getRatingCatName() {
		return ratingCatName;
	}


	public void setRatingCatName(String ratingCatName) {
		this.ratingCatName = ratingCatName;
	}


	public String getRatingType() {
		return ratingType;
	}


	public void setRatingType(String ratingType) {
		this.ratingType = ratingType;
	}


	public String getRatingTypeName() {
		return ratingTypeName;
	}


	public void setRatingTypeName(String ratingTypeName) {
		this.ratingTypeName = ratingTypeName;
	}


	public String getRating() {
		return rating;
	}


	public void setRating(String rating) {
		this.rating = rating;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getSkillId() {
		return skillId;
	}


	public void setSkillId(String skillId) {
		this.skillId = skillId;
	}


	public String getSkillName() {
		return skillName;
	}


	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}


	@Override
	public String toString() {
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr;
		try {
			jsonStr = mapperObj.writeValueAsString(this);
		} catch (IOException ex) {

			jsonStr = ex.toString();
		}
		return jsonStr;
	}

}
