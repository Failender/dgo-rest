package de.failender.dgo.rest.fantasygrounds;

public class ExportCharacterDTO {

    private Long id;
    private Integer campaignId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }
}
