package hd.sphinx.sync.util.scheduler;

public class SchedulerManager {

    private Scheduler scheduler;

    public SchedulerManager() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            scheduler = new FoliaScheduler();
        } catch (ClassNotFoundException e) {
            scheduler = new SpigotScheduler();
        }
    }

    public Scheduler getScheduler() {
        return scheduler;
    }
}
