<template>
  <!-- Header -->
  <div class="relative bg-emerald-600 md:pt-5 pb-5 pt-1">
    <div class="px-4 md:px-10 mx-auto w-full">
      <div>
        <!-- Card stats -->
        <div class="flex flex-wrap">
          <div class="w-full lg:w-6/12 xl:w-1/5 px-4">
            <card-stats
              statSubtitle="SERVICE-REGISTRY"
              :statTitle="serviceRegistry ? 'Online' : 'Offline'"
              statArrow="up"
              statPercent="3.48"
              statPercentColor="text-emerald-500"
              statDescripiron="Since last month"
              statIconName="far fa-chart-bar"
              :statIconColor="serviceRegistry ? green : red"
            />
          </div>
          <div class="w-full lg:w-6/12 xl:w-1/5 px-4">
            <card-stats
              statSubtitle="DB-SERVICE"
              :statTitle="dbService ? 'Online' : 'Offline'"
              statArrow="down"
              statPercent="3.48"
              statPercentColor="text-red-500"
              statDescripiron="Since last week"
              statIconName="fas fa-chart-pie"
              :statIconColor="dbService ? green : red"
            />
          </div>
          <div class="w-full lg:w-6/12 xl:w-1/5 px-4">
            <card-stats
              statSubtitle="PARSING-SERVICE"
              :statTitle="parsingService ? 'Online' : 'Offline'"
              statArrow="down"
              statPercent="1.10"
              statPercentColor="text-orange-500"
              statDescripiron="Since yesterday"
              statIconName="fas fa-users"
              :statIconColor="parsingService ? green : red"
            />
          </div>
          <div class="w-full lg:w-6/12 xl:w-1/5 px-4">
            <card-stats
              statSubtitle="USER-SERVICE"
              :statTitle="telegramUserService ? 'Online' : 'Offline'"
              statArrow="up"
              statPercent="12"
              statPercentColor="text-emerald-500"
              statDescripiron="Since last month"
              statIconName="fas fa-percent"
              :statIconColor="telegramUserService ? green : red"
            />
          </div>
          <div class="w-full lg:w-6/12 xl:w-1/5 px-4">
            <card-stats
              statSubtitle="LOGISTIC-SERVICE"
              :statTitle="logisticService ? 'Online' : 'Offline'"
              statArrow="up"
              statPercent="12"
              statPercentColor="text-emerald-500"
              statDescripiron="Since last month"
              statIconName="fas fa-percent"
              :statIconColor="logisticService ? green : red"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CardStats from "@/components/cards/CardStats";

export default {
  middleware: ['auth-user'],
  components: {
    CardStats,
  },

  async created() {
    await this.$store.dispatch('healthCheck/servicesHealthCheck')
  },

  data: () => ({
    red: 'bg-red-500 ',
    green: 'bg-green-500'
  }),

  computed: {
    serviceRegistry() { return this.$store.getters['healthCheck/getServiceRegistryStatus'] },
    dbService() { return this.$store.getters['healthCheck/getDbServiceStatus'] },
    parsingService() { return this.$store.getters['healthCheck/getParsingServiceStatus'] },
    telegramUserService() { return this.$store.getters['healthCheck/getTelegramUserServiceStatus'] },
    logisticService() { return this.$store.getters['healthCheck/getLogisticServiceStatus'] }
  }
};
</script>
